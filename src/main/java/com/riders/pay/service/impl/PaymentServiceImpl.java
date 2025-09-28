package com.riders.pay.service.impl;


import com.riders.pay.entities.Account;
import com.riders.pay.model.AccountModel;
import com.riders.pay.model.FundTransferModel;
import com.riders.pay.model.ResponseModel;
import com.riders.pay.repository.PaymentRepository;
import com.riders.pay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.*;

@Service(value = "payment service")
public class PaymentServiceImpl implements PaymentService {

    public static final String FUND_TRANSFER_FAILED = "Fund Transfer Failed..!";
    public static final String SUCCESS_CODE = "00";
    public static final String FAILED_CODE = "01";

    @Autowired
    private PaymentRepository repository;

    @Override
    public ResponseEntity<?> registerAccount(AccountModel model) {

        ResponseModel responseModel = new ResponseModel();
        Account existingData = repository.getByVpa(model.getVpa().toLowerCase());
        Object object = null;

        if (existingData==null) {
            Account account = getAccountEntity(model);
            object = repository.save(account);

            if (object != null) {
                responseModel = getResponseModel("Registered Successfully!", SUCCESS_CODE, object);
            } else {
                responseModel = getResponseModel( "Registration Fail!", "error while registering vpa.", null);
            }
        } else {
            responseModel = getResponseModel( "Registration Fail!", model.getVpa() + " VPA already registered.", null);
        }
        return new ResponseEntity(responseModel, OK);
    }

    public ResponseModel getResponseModel(String status, String errMsg, Object object) {
        ResponseModel model = new ResponseModel();
        model.setStatus(status);
        model.setErrorMsg(errMsg);
        model.setData(object);
        return model;
    }

    public Account getAccountEntity(AccountModel model) {
        Account account = new Account();
        account.setName(model.getName());
        account.setVpa(model.getVpa().toLowerCase());
        account.setAccNum(model.getAccNum());
        account.setBalance(model.getBalance());
        account.setStatus(model.getStatus());
        account.setName(model.getName());
        return account;
    }

    @Override
    public ResponseEntity<?> topup(String vpa, BigDecimal topup) {

        ResponseModel responseModel = new ResponseModel();
        Account existingData = repository.getByVpa(vpa.toLowerCase());
        int updatedRows = 0;
        BigDecimal totalAmt = BigDecimal.ZERO;

        if (existingData!=null) {
            totalAmt = existingData.getBalance().add(topup);
            updatedRows =  repository.updateBalanceByVpa(vpa, totalAmt);

            if (updatedRows > 0) {
                responseModel = getResponseModel("Topup updated successfully, current balance is " + totalAmt, SUCCESS_CODE, null);
            } else {
                responseModel = getResponseModel("No Topup updated, current balance is " + totalAmt, FAILED_CODE, null);
            }
        } else {
            responseModel = getResponseModel(FUND_TRANSFER_FAILED, "No account found with that VPA::"+vpa, null);
        }
        return new ResponseEntity<>(responseModel, OK);
    }

    @Override
    public ResponseEntity<?> fundTransfer(FundTransferModel model) {
        ResponseModel responseModel = new ResponseModel();
        Account existingPayer = repository.getByVpa(model.getPayerVpa().toLowerCase());
        Account existingPayee = repository.getByVpa(model.getPayerVpa().toLowerCase());
        int updatedPayees, updatedPayers = 0;

        if (!isBlank(String.valueOf(existingPayer))) {
            if (!isBlank(String.valueOf(existingPayee))) {
                if (existingPayer.getStatus().equals(AccountModel.Status.Active) &&
                        existingPayee.getStatus().equals(AccountModel.Status.Active)){
                    System.out.println("============@@@@@@@@@@@@@@@==========" );
                    BigDecimal payeeAmount = existingPayee.getBalance().add(model.getAmount());
                    BigDecimal payerAmount = existingPayer.getBalance().subtract(model.getAmount());

                    updatedPayees =  repository.updateBalanceByVpa(model.getPayeeVpa(), payeeAmount);
                    if (updatedPayees > 0) {
                        updatedPayers = repository.updateBalanceByVpa(model.getPayerVpa(), payerAmount);

                        if (updatedPayers > 0) {
                            responseModel = getResponseModel("Fund Transfer successfully!", SUCCESS_CODE, null);
                        } else {
                            responseModel = getResponseModel(FUND_TRANSFER_FAILED, FAILED_CODE, null);
                        }
                    }else {
                        responseModel = getResponseModel(FUND_TRANSFER_FAILED, "Payer/Payee VPA not Active.", null);
                    }
                }
            } else {
                responseModel = getResponseModel(FUND_TRANSFER_FAILED, "Payee VPA does not exist.", null);
            }
        } else {
            responseModel = getResponseModel(FUND_TRANSFER_FAILED, "Payer VPA does not exist.", null);
        }
        return new ResponseEntity<>(responseModel, OK);
    }

    public Boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}
