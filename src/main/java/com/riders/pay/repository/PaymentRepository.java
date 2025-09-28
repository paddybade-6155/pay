package com.riders.pay.repository;

import com.riders.pay.entities.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PaymentRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM accounts WHERE vpa = :vpa", nativeQuery = true)
    Account getByVpa(@Param("vpa") String vpa);

    @Modifying
    @Transactional
    @Query(value = "UPDATE accounts SET balance = :balance WHERE vpa = :vpa", nativeQuery = true)
    int updateBalanceByVpa(@Param("vpa") String vpa, @Param("balance") BigDecimal balance);
}
