package com.bankmanagement.bankmanagementapi.repository;

import com.bankmanagement.bankmanagementapi.entity.Bank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankRepoTest {

    @Autowired
    BankRepo bankRepo;

    @Test
    public void bankDetailsSaving(){
        saveBank();
        Optional<Bank> bank = bankRepo.findByBankName("SBI");
        assertEquals("SBI",bank.get().getBankName());
        assertEquals("Nagercoil",bank.get().getBankAddress());
    }

    public void saveBank() {
        Bank bank = new Bank();
        bank.setBankName("SBI");
        bank.setBankAddress("Nagercoil");
        bankRepo.save(bank);
    }
}
