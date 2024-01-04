package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.BankDTO;
import com.bankmanagement.bankmanagementapi.entity.Bank;
import com.bankmanagement.bankmanagementapi.entity.Branch;
import com.bankmanagement.bankmanagementapi.exception.BankDetailsNotFound;
import com.bankmanagement.bankmanagementapi.repository.BankRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankServiceImplTest {

    @Mock
    private BankRepo bankRepo;

    @InjectMocks
    private BankServiceImpl bankService;

    @Test
    public void findAllBanks_returnValid_AllBanks() throws BankDetailsNotFound{
        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("SBI");
        bank.setBankAddress("Nagercoil");

        List<Bank> bankList = new ArrayList<>();
        bankList.add(bank);

        when(bankRepo.findAll()).thenReturn(bankList);

        List<BankDTO> bankDTOList = bankService.findAllBanks();
        assertEquals(1,bankDTOList.size());
    }

    @Test(expected = BankDetailsNotFound.class)
    public void findAllBanks_returnInValid_AllBanks() throws BankDetailsNotFound{
        when(bankRepo.findAll()).thenReturn(null);
        bankService.findAllBanks();
    }

    @Test
    public void findAllBanks_returnValid_AllBanksAndBranches() throws BankDetailsNotFound{
        Bank bank = new Bank();
        bank.setBankCode(456);
        bank.setBankName("SBI");
        bank.setBankAddress("Nagercoil");

        Branch branch = new Branch();
        branch.setBranchId(1);
        branch.setBranchName("Nagercoil");
        branch.setBranchName("TamilNadu");

        Set<Branch> branchSet = new HashSet<>();
        branchSet.add(branch);

        bank.setBranch(branchSet);
        List<Bank> bankList = new ArrayList<>();
        bankList.add(bank);

        when(bankRepo.findAll()).thenReturn(bankList);

        List<BankDTO> bankDTOList = bankService.findAllBanks();
        assertEquals(1,bankDTOList.size());
    }

    @Test
    public void findByBankCode_WhenValidDetailsEntered_ReturnBankDetails() throws BankDetailsNotFound{
        Bank bank = new Bank();
        bank.setBankCode(456);
        bank.setBankName("SBI");
        bank.setBankAddress("Nagercoil");

        when(bankRepo.findById(bank.getBankCode())).thenReturn(Optional.of(bank));

        BankDTO bankDTO = bankService.findBankDetailsById(999);
        assertEquals("SBI",bankDTO.getBankName());
        assertEquals("Nagercoil",bankDTO.getBankAddress());
    }

    @Test(expected = BankDetailsNotFound.class)
    public void findByBankCode_WhenInValidDetailsEntered_ReturnBankDetails() throws BankDetailsNotFound{
        Bank bank = new Bank();
        bank.setBankCode(456);
        bank.setBankName("SBI");
        bank.setBankAddress("Nagercoil");

        when(bankRepo.findById(bank.getBankCode())).thenReturn(Optional.ofNullable(null));

        BankDTO bankDTO = bankService.findBankDetailsById(1);
    }
}
