package com.bankmanagement.bankmanagementapi.repository;

import com.bankmanagement.bankmanagementapi.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankRepo extends JpaRepository <Bank, Integer> {

    @Query(value = "SELECT bank FROM Bank bank WHERE bankName=:name")
    Optional<Bank> findByBankName(@Param("name") String bankName);

    @Query(value = "SELECT bank FROM Bank bank WHERE bankAddress=:bankAddress")
    Bank findByAddress(String bankAddress);

    @Modifying
    @Query("DELETE FROM Bank b WHERE b.bankName = :name")
    void deleteByName(@Param("name") String bankName);
}
