package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.BankDTO;
import com.bankmanagement.bankmanagementapi.dto.BranchDTO;
import com.bankmanagement.bankmanagementapi.entity.Bank;
import com.bankmanagement.bankmanagementapi.entity.BankRequest;
import com.bankmanagement.bankmanagementapi.entity.Branch;
import com.bankmanagement.bankmanagementapi.exception.BankDetailsNotFound;
import com.bankmanagement.bankmanagementapi.repository.BankRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepo bankRepo;

// Get All the Banks and Branches in List

    @Override
    public List<BankDTO> findAllBanks() throws BankDetailsNotFound {
        log.info("Inside BankServiceImpl.findAllBanks()");

        List<Bank> bankList = bankRepo.findAll();

        if (CollectionUtils.isEmpty(bankList)) {
            log.error("Bank details not found");
            throw new BankDetailsNotFound("Bank Details Not Found in DB");
        }

        List<BankDTO> bankDTOList = bankList.stream().map(bank -> {
            BankDTO bankDTO = new BankDTO();
            bankDTO.setBankName(bank.getBankName());
            bankDTO.setBankAddress(bank.getBankAddress());

            Set<Branch> branchSet = bank.getBranch();

            List<BranchDTO> branchDTOList = branchSet.stream().map(branch -> {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(branch.getBranchName());
                branchDTO.setBranchAddress(branch.getBranchAddress());

                return branchDTO;
            }).collect(Collectors.toList());

            bankDTO.setBranchDto(branchDTOList);

            return bankDTO;
        }).collect(Collectors.toList());

        log.info("End of BankServiceImpl.findAllBanks()");

        return bankDTOList;
    }

// Find the Bank and its respective Branches using Bank Code (Primary)

    @Override
    public BankDTO findBankDetailsById(int bankCode) throws BankDetailsNotFound {

        log.info("Inside BankServiceImpl.findBankDetailsById Bank Code:{}", bankCode);

        Optional<Bank> bank = bankRepo.findById(bankCode);

        if (bank.isEmpty()) {
            log.error("Bank Details Not available for ID:{}", bankCode);
            throw new BankDetailsNotFound("Bank Details Not Found");
        }

        Bank bank1 = bank.get();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank1.getBankName());
        bankDTO.setBankAddress(bank1.getBankAddress());

        Set<Branch> branchSet = bank1.getBranch();

        List<BranchDTO> branchDTOList = branchSet.stream().map(branch -> {
            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setBranchName(branch.getBranchName());
            branchDTO.setBranchAddress(branch.getBranchAddress());

            return branchDTO;
        }).collect(Collectors.toList());

        log.info("End of BankServiceImpl.findBankDetailsById Bank Code:{}", bankCode);
        bankDTO.setBranchDto(branchDTOList);
        return bankDTO;
    }

// Find the Bank Details and its Branches based upon the Bank Name

    @Override
    public BankDTO getByName(String bankName) throws BankDetailsNotFound {

        log.info("Inside BankServiceImpl.getByName Bank Name:{}", bankName);

        Optional<Bank> bank = bankRepo.findByBankName(bankName);

        if(bank.isEmpty()){
            log.error("Bank Details Not available for Name:{}", bankName);
            throw new BankDetailsNotFound("Bank Details Not Found");
        }

        Bank bank1 = bank.get();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankAddress(bank1.getBankAddress());

        Set<Branch> branchSet = bank1.getBranch();

        List<BranchDTO> branchDTOList = branchSet.stream().map(branch -> {
            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setBranchName(branch.getBranchName());
            branchDTO.setBranchAddress(branch.getBranchAddress());

            return branchDTO;
        }).collect(Collectors.toList());

        log.info("End of BankServiceImpl.findBankDetailsById Bank Name:{}", bankName);
        bankDTO.setBranchDto(branchDTOList);

        return bankDTO;
    }

    @Override
    public BankDTO saveBank(BankRequest bankRequest) throws BankDetailsNotFound {

        log.info("Inside BankServiceImpl.saveBank ,bankRequest:{} ", bankRequest);

        Bank bank = new Bank();
        bank.setBankName(bankRequest.getBankName());
        bank.setBankAddress(bankRequest.getBankAddress());

        Bank bank1 = bankRepo.save(bank);

        if(Objects.isNull(bank1)){
            log.error("Bank details not saved");
            throw new BankDetailsNotFound("Bank details not saved");
        }

        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank1.getBankName());
        bankDTO.setBankAddress(bank1.getBankAddress());


        log.info("End of BankServiceImpl.saveBank Bank Details:{}", bankRequest);
        return bankDTO;
    }

    @Override
    public BankDTO updateBankDetails(BankRequest bankRequest) throws BankDetailsNotFound {
        log.info("Inside the BankServiceImpl.updateBankDetails ,bankRequest:{} ", bankRequest);

        Bank bank = bankRepo.findById(bankRequest.getBankCode()).get();

//        if(bank.getBankCode() != bankRequest.getBankCode()){
//            bank.setBankCode(bankRequest.getBankCode());
//        }

        if(!Objects.isNull(bankRequest.getBankName())) {
            bank.setBankName(bankRequest.getBankName());
        }
        if(!Objects.isNull(bankRequest.getBankAddress())) {
            bank.setBankAddress(bankRequest.getBankAddress());
        }
        Bank bank1 = bankRepo.save(bank);

        if(Objects.isNull(bank1)){
            log.error("Bank details not saved");
            throw new BankDetailsNotFound("Bank details not saved");
        }

        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankCode(bank1.getBankCode());
        bankDTO.setBankName(bank1.getBankName());
        bankDTO.setBankAddress(bank1.getBankAddress());

        log.info("End of BankServiceImpl.updateBankDetails Bank Details:{}", bankRequest);
        return bankDTO;
    }

    @Override
    public String deleteBank(int bankCode) throws BankDetailsNotFound {
        log.info("Inside the BankServiceImpl.deleteBank");

        Optional<Bank> bank = bankRepo.findById(bankCode);

        if(bank.isEmpty()){
            log.error("Bank Details not Found for the Code:{}",bankCode);
            throw new BankDetailsNotFound("Bank Details not Found");
        }

        try {
            bankRepo.delete(bank.get());
        }
        catch (Exception e){
            log.error("Error While deleting bank Details ",e);
            throw new BankDetailsNotFound("Error While deleting bank Details ");
        }
        return "Bank Details Deleted";
    }

    @Override
    @Transactional
    public String deleteBankByName(String bankName) throws BankDetailsNotFound {
        log.info("Inside the BankServiceImpl.deleteBankByName");

        Optional<Bank> bank = bankRepo.findByBankName(bankName);

        if(Objects.isNull(bank)){
            log.error("No details found for Bank Name:{}",bankName);
            throw new BankDetailsNotFound("No details found for the Specified Bank Name");
        }

        try {
            bankRepo.deleteByName(bankName);
        }
        catch (Exception e){
            log.error("Error While deleting Bank Details",e);
            throw new BankDetailsNotFound("Error While deleting bank Details ");
        }
        return "Bank Details Deleted";
    }
}
