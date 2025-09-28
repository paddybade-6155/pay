package com.riders.pay.service;

import com.riders.pay.model.AccountModel;
import com.riders.pay.model.FundTransferModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface PaymentService {

    ResponseEntity<?> registerAccount(AccountModel accountModel);

    ResponseEntity<?> topup(String vpa, BigDecimal topup);

    ResponseEntity<?> fundTransfer(FundTransferModel model);
}
