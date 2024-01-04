package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.LoanDTO;
import com.bankmanagement.bankmanagementapi.entity.LoanRequest;
import com.bankmanagement.bankmanagementapi.exception.LoanDetailsNotFound;

import java.util.List;

public interface LoanService {

    List<LoanDTO> findAllLoans() throws LoanDetailsNotFound;

    LoanDTO addDetails(LoanRequest loanRequest) throws LoanDetailsNotFound;

    LoanDTO updateLoan(LoanRequest loanRequest) throws LoanDetailsNotFound;
}
