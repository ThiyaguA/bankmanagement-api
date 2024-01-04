package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.BranchDTO;
import com.bankmanagement.bankmanagementapi.entity.Bank;
import com.bankmanagement.bankmanagementapi.entity.Branch;
import com.bankmanagement.bankmanagementapi.entity.BranchRequest;
import com.bankmanagement.bankmanagementapi.exception.BranchNotFound;
import com.bankmanagement.bankmanagementapi.repository.BankRepo;
import com.bankmanagement.bankmanagementapi.repository.BranchRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private BankRepo bankRepo;

    @Override
    public List<BranchDTO> findAllBranches() throws BranchNotFound {
        log.info("Inside BranchServiceImpl.findAllBranches");

        List<Branch> branchList = branchRepo.findAll();

        if(CollectionUtils.isEmpty(branchList)){
            log.error("Branch details Not found in DB");
            throw new BranchNotFound("Branch details Not found in DB");
        }

        List<BranchDTO> branchDTOList = branchList.stream().map(branch -> {
            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setBranchName(branch.getBranchName());
            branchDTO.setBranchAddress(branch.getBranchAddress());

            return branchDTO;
        }).collect(Collectors.toList());

        log.info("End of BranchServiceImpl.findAllBranches");
        return branchDTOList;
    }

    @Override
    public BranchDTO saveAllBranches(BranchRequest branchRequest) throws BranchNotFound {
        log.info("Inside the BranchServiceImpl.saveAllBranches");

        if(Objects.isNull(branchRequest)){
            log.error("No Branch Details are Saved");
            throw new BranchNotFound("No Branch Details are Saved");
        }

        Branch branch = new Branch();
        branch.setBranchName(branchRequest.getBranchName());
        branch.setBranchAddress(branchRequest.getBranchAddress());

        Bank bank = bankRepo.findByAddress(branchRequest.getBranchAddress());

        branch.setBank(bank);

        Branch branch1 = branchRepo.save(branch);

        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName(branch1.getBranchName());
        branchDTO.setBranchAddress(branch1.getBranchAddress());

        if (branch1.getBank() != null) {
            branchDTO.setBankCode(branch1.getBank().getBankCode());
        }
        else {
            log.error("Bank information is missing for the branch");
        }

        log.info("End of BranchServiceImpl.saveAllBranches");
        return branchDTO;
    }

    @Override
    public BranchDTO updateBranch(BranchRequest branchRequest) throws BranchNotFound {
        log.info("Inside the BranchServiceImpl.updateBranch");

        Branch branch = branchRepo.findById(branchRequest.getBankCode()).get();

        if(!Objects.isNull(branchRequest.getBranchName())){
            branch.setBranchName(branchRequest.getBranchName());
        }
        if(!Objects.isNull(branchRequest.getBranchAddress())){
            branch.setBranchAddress(branchRequest.getBranchAddress());
        }
        Bank bank = new Bank();
        branch.setBank(bankRepo.findById(branchRequest.getBankCode()).get());

        Branch branch1 = branchRepo.save(branch);

        if(Objects.isNull(branch1)){
            log.error("Branch details not saved");
            throw new BranchNotFound("Branch details not saved");
        }

        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBankCode(branch1.getBank().getBankCode());
        branchDTO.setBranchName(branch1.getBranchName());
        branchDTO.setBranchAddress(branch1.getBranchAddress());

        log.info("End of BranchServiceImpl.updateBranch");
        return branchDTO;
    }
}
