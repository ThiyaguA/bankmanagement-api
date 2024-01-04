package com.bankmanagement.bankmanagementapi.controller;

import com.bankmanagement.bankmanagementapi.dto.LoanDTO;
import com.bankmanagement.bankmanagementapi.entity.LoanRequest;
import com.bankmanagement.bankmanagementapi.exception.LoanDetailsNotFound;
import com.bankmanagement.bankmanagementapi.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("api/v1/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoanDetails(){

        List<LoanDTO> loanDTOList = null;

        try {
            loanDTOList = loanService.findAllLoans();
            log.info("Loan Details :{}" , loanDTOList);
        }
        catch (LoanDetailsNotFound e){
            log.error("Loan Details Not Found " ,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error in Manipulating Data" , e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of LoanController.getAllLoanDetails");
        return new ResponseEntity<>(loanDTOList , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LoanDTO> addDetails(@RequestBody LoanRequest loanRequest){
        log.info("Inside the LoanController.addDetails");

        if(Objects.isNull(loanRequest)){
            log.error("Loan Details not Valid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LoanDTO loanDTO = null;

        try {
            loanDTO = loanService.addDetails(loanRequest);
            log.info("Loan Details:{}" , loanRequest);
        }
        catch (LoanDetailsNotFound e){
            log.error("Loan Details not Found " , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error While Manipulating Data ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of LoanController.addDetails");
        return new ResponseEntity<>(loanDTO,HttpStatus.OK);
    }

    public ResponseEntity<LoanDTO> updateLoan(@RequestBody LoanRequest loanRequest){
        log.info("Inside the LoanController.updateLoan");

        if(Objects.isNull(loanRequest)){
            log.error("Loan Details are not valid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LoanDTO loanDTO = null;

        try {
            loanDTO = loanService.updateLoan(loanRequest);
        }
        catch (LoanDetailsNotFound e){
            log.error("Loan Details not found",e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error While Manipulating Data",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of LoanController.updateLoan");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
