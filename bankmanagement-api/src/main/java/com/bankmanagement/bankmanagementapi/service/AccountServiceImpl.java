package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.AccountDTO;
import com.bankmanagement.bankmanagementapi.entity.Account;
import com.bankmanagement.bankmanagementapi.entity.AccountRequest;
import com.bankmanagement.bankmanagementapi.entity.Branch;
import com.bankmanagement.bankmanagementapi.entity.Customer;
import com.bankmanagement.bankmanagementapi.exception.AccountDetailsNotFound;
import com.bankmanagement.bankmanagementapi.repository.AccountRepo;
import com.bankmanagement.bankmanagementapi.repository.BranchRepo;
import com.bankmanagement.bankmanagementapi.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private BranchRepo branchRepo;
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<AccountDTO> findAllAccounts() throws AccountDetailsNotFound {

        log.info("Inside AccountServiceImpl.findAllAccounts");

        List<Account> accountList = accountRepo.findAll();

        if(CollectionUtils.isEmpty(accountList)){
            log.error("Account Details are not available");
            throw new AccountDetailsNotFound("Account Details are not available");
        }

        List<AccountDTO> accountDTOList = accountList.stream().map(account -> {
            AccountDTO accountDTO = new AccountDTO();

            accountDTO.setAccountNumber(account.getAccountNumber());
            accountDTO.setAccountType(account.getAccountType());
            accountDTO.setAccountBalance(account.getAccountBalance());

            return accountDTO;
        }).collect(Collectors.toList());

        log.info("End of AccountServiceImpl.findAllAccounts");
        return accountDTOList;
    }

    @Override
    public AccountDTO saveAccountDetails(AccountRequest accountRequest) throws AccountDetailsNotFound {

        log.info("Inside the AccountServiceImpl.saveAccountDetails");

        if(Objects.isNull(accountRequest)){
            log.error("No account Details are Saved");
            throw new AccountDetailsNotFound("No account Details are Saved");
        }

        Account account = new Account();
        account.setAccountBalance(accountRequest.getAccountBalance());
        account.setAccountType(accountRequest.getAccountType());

        Customer customer = customerRepo.findById(accountRequest.getCustomerId()).orElseThrow();
        account.setCustomer(customer);

        Branch branch = branchRepo.findById(accountRequest.getBranchId()).orElseThrow();
        account.setBranch(branch);

        Account account1 = accountRepo.save(account);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountType(account1.getAccountType());
        accountDTO.setAccountBalance(account1.getAccountBalance());

        if (account1.getBranch() != null || account1.getCustomer() != null) {
            accountDTO.setCustomerId(account1.getCustomer().getCusId());
            accountDTO.setBranchId(account1.getBranch().getBranchId());
        }
        else {
            log.error("Account information is missing for the Branch or Customer");
        }

        log.error("End of AccountServiceImpl.saveAccountDetails");
        return accountDTO;
    }

    @Override
    public AccountDTO updateAccount(AccountRequest accountRequest) throws AccountDetailsNotFound {
        log.info("Inside the AccountServiceImpl.updateAccount");

        Account account = accountRepo.findById(accountRequest.getAccountNumber()).get();

        if(!Objects.isNull(accountRequest.getAccountType())){
            account.setAccountType(accountRequest.getAccountType());
        }
        if(!Objects.isNull(accountRequest.getAccountBalance())){
            account.setAccountBalance(accountRequest.getAccountBalance());
        }
        Branch branch = new Branch();
        account.setBranch(branchRepo.findById(accountRequest.getBranchId()).get());

        Customer customer = new Customer();
        account.setCustomer(customerRepo.findById(accountRequest.getCustomerId()).get());

        Account account1 = accountRepo.save(account);

        if(Objects.isNull(account1)){
            log.error("Account details not saved");
            throw new AccountDetailsNotFound("Account details not saved");
        }

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountType(account1.getAccountType());
        accountDTO.setAccountBalance(account1.getAccountBalance());
        accountDTO.setBranchId(account1.getBranch().getBranchId());
        accountDTO.setCustomerId(account1.getCustomer().getCusId());

        log.info("End of AccountServiceImpl.updateAccount");
        return accountDTO;
    }
}
