package com.bankmanagement.bankmanagementapi.controller;

import com.bankmanagement.bankmanagementapi.dto.CustomerDTO;
import com.bankmanagement.bankmanagementapi.entity.CustomerRequest;
import com.bankmanagement.bankmanagementapi.exception.CustomerDetailsNotFound;
import com.bankmanagement.bankmanagementapi.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllAccounts(){

        List<CustomerDTO> customerDTOList = null;

        try {
            customerDTOList = customerService.findAllCustomers();
            log.info("Customer Details :{}" , customerDTOList);
        }
        catch (CustomerDetailsNotFound e){
            log.error("Customer Details Not Found" , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error in Manipulating Data" ,e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of CustomerController.getAllAccounts");
        return new ResponseEntity<>(customerDTOList , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO>  saveCustomer(@RequestBody CustomerRequest customerRequest){
        log.info("Inside the CustomerController.saveCustomer");

        if (Objects.isNull(customerRequest)){
            log.error("Customer Details not Valid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CustomerDTO customerDTO = null;

        try {
            customerDTO = customerService.addDetails(customerRequest);
            log.info("Customer Details, Customer DTO:{}",customerRequest);
        }
        catch (CustomerDetailsNotFound e){
            log.error("Customer Details Not Found" , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error While Manipulating Data",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of CustomerController.saveCustomer");
        return new ResponseEntity<>(customerDTO , HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerRequest customerRequest){
        log.info("Inside the CustomerController.updateCustomer");

        if(Objects.isNull(customerRequest)){
            log.error("No details are Saved");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CustomerDTO customerDTO = null;

        try {
            customerDTO = customerService.updateCustomer(customerRequest);
            log.info("Customer Details :{}",customerRequest);
        }
        catch (CustomerDetailsNotFound e){
            log.error("Customer Details not Found",e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error while manipulating data",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of CustomerController.updateCustomer");
        return new ResponseEntity<>(customerDTO,HttpStatus.OK);
    }
}
