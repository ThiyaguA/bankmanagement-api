package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.CustomerDTO;
import com.bankmanagement.bankmanagementapi.entity.Customer;
import com.bankmanagement.bankmanagementapi.entity.CustomerRequest;
import com.bankmanagement.bankmanagementapi.exception.CustomerDetailsNotFound;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<CustomerDTO> findAllCustomers() throws CustomerDetailsNotFound {

        log.info("Inside CustomerServiceImpl.findAllCustomers");

        List<Customer> customerList = customerRepo.findAll();

        if(CollectionUtils.isEmpty(customerList)){
            log.error("Customer Details not found");
            throw new CustomerDetailsNotFound("Customer Details not found");
        }

        List<CustomerDTO> customerDTOList = customerList.stream().map(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCusName(customer.getCusName());
            customerDTO.setCusPhone(customer.getCusPhone());
            customerDTO.setCusAddress(customer.getCusAddress());

            return customerDTO;
        }).collect(Collectors.toList());

        log.info("End of CustomerServiceImpl.findAllCustomers");
        return customerDTOList;
    }

    @Override
    public CustomerDTO addDetails(CustomerRequest customerRequest) throws CustomerDetailsNotFound {
        log.info("Inside the CustomerServiceImpl.addDetails");

        Customer customer = new Customer();
        customer.setCusAddress(customerRequest.getCusAddress());
        customer.setCusName(customerRequest.getCusName());
        customer.setCusPhone(customerRequest.getCusPhone());

        Customer customer1 = customerRepo.save(customer);

        if(Objects.isNull(customer1)){
            log.error("No Details found in Customer DB");
            throw new CustomerDetailsNotFound("No Details found in Customer DB");
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCusAddress(customer1.getCusAddress());
        customerDTO.setCusName(customer1.getCusName());
        customerDTO.setCusPhone(customer1.getCusPhone());

        log.info("End of CustomerServiceImpl.addDetails");
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerRequest customerRequest) throws CustomerDetailsNotFound {
        log.info("Inside the CustomerServiceImpl.updateCustomer");

        Customer customer = customerRepo.findById(customerRequest.getCusId()).get();

        if(!Objects.isNull(customerRequest.getCusAddress())){
            customer.setCusAddress(customerRequest.getCusAddress());
        }
        if(!Objects.isNull(customerRequest.getCusName())){
            customer.setCusName(customerRequest.getCusName());
        }
        if(!Objects.isNull(customerRequest.getCusPhone())){
            customer.setCusPhone(customerRequest.getCusPhone());
        }

        Customer customer1 = customerRepo.save(customer);

        if(Objects.isNull(customer1)){
            log.error("Customer Details are not Saved in DB");
            throw new CustomerDetailsNotFound("Customer Details are not Saved in DB");
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCusAddress(customer1.getCusAddress());
        customerDTO.setCusName(customer1.getCusName());
        customerDTO.setCusPhone(customer1.getCusPhone());

        log.info("End of CustomerServiceImpl.updateCustomer");
        return customerDTO;
    }
}
