package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.BankDTO;
import com.bankmanagement.bankmanagementapi.entity.BankRequest;
import com.bankmanagement.bankmanagementapi.exception.BankDetailsNotFound;

import java.util.List;

public interface BankService {

    List<BankDTO> findAllBanks() throws BankDetailsNotFound;

    BankDTO findBankDetailsById(int bankCode) throws BankDetailsNotFound;

    BankDTO getByName(String bankName) throws BankDetailsNotFound;

    BankDTO saveBank(BankRequest bankRequest) throws BankDetailsNotFound;

    BankDTO updateBankDetails(BankRequest bankRequest) throws BankDetailsNotFound;

    String deleteBank(int bankCode) throws BankDetailsNotFound;

    String deleteBankByName(String bankName) throws BankDetailsNotFound;
}
