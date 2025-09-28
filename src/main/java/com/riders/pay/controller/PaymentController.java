package com.riders.pay.controller;

import com.riders.pay.controller.Constant;
import com.riders.pay.model.AccountModel;
import com.riders.pay.model.FundTransferModel;
import com.riders.pay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = Constant.REGISTER_ACC)
    public ResponseEntity<?> registerAccount(@RequestBody AccountModel accountModel) {
        System.out.println("accountModel.toString() = " + accountModel.toString());
        return paymentService.registerAccount(accountModel);
    }

    @PostMapping(value = Constant.TOPUP)
    public ResponseEntity<?> topupMoney(@RequestParam String vpa, BigDecimal topup) {
        System.out.println("request received for topup money..!");
        return paymentService.topup(vpa, topup);
    }

    @PostMapping(value = Constant.FUND_TRANSFER)
    public ResponseEntity<?> topupMoney(@RequestBody FundTransferModel model) {
        System.out.println("request received for topup money..!");
        return paymentService.fundTransfer(model);
    }

}
