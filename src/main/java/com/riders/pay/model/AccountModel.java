package com.riders.pay.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountModel {

    private Long id;
    private String name;
    private String vpa;
    private String accNum;
    private BigDecimal balance;
    private String currency;
    private Status status;

    public enum Status {
        Active ,
        Inactive
    }
}