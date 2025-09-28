package com.riders.pay.entities;

import com.riders.pay.entities.Auditable;
import com.riders.pay.model.AccountModel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "accounts")
public class Account extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vpa")
    private String vpa;

    @Column(name = "acc_no", unique = true, nullable = false)
    private String accNum;

    @Column(precision = 15, scale = 2)
    private BigDecimal balance;

    private String currency = "INR";

    private AccountModel.Status status;

}
