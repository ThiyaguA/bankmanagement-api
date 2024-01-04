package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.AccountDTO;
import com.bankmanagement.bankmanagementapi.entity.AccountRequest;
import com.bankmanagement.bankmanagementapi.entity.BranchRequest;
import com.bankmanagement.bankmanagementapi.exception.AccountDetailsNotFound;

import java.util.List;

public interface AccountService {

    List<AccountDTO> findAllAccounts() throws AccountDetailsNotFound;

    AccountDTO saveAccountDetails(AccountRequest accountRequest) throws AccountDetailsNotFound;

    AccountDTO updateAccount(AccountRequest accountRequest) throws AccountDetailsNotFound;
}
