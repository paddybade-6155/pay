package com.riders.pay.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundTransferModel {

    private String txnId;
    private String payerVpa;
    private String payeeVpa;
    private TxnType type;
    private String payerName;
    private BigDecimal amount;

    public enum TxnType {
        CREDIT ,
        DEBIT
    }
}
