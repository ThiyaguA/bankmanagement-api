package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.CustomerDTO;
import com.bankmanagement.bankmanagementapi.entity.CustomerRequest;
import com.bankmanagement.bankmanagementapi.exception.CustomerDetailsNotFound;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAllCustomers() throws CustomerDetailsNotFound;

    CustomerDTO addDetails(CustomerRequest customerRequest) throws CustomerDetailsNotFound;

    CustomerDTO updateCustomer(CustomerRequest customerRequest) throws CustomerDetailsNotFound;
}
