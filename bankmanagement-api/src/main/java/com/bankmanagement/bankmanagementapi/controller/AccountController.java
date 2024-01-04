package com.bankmanagement.bankmanagementapi.controller;

import com.bankmanagement.bankmanagementapi.dto.AccountDTO;
import com.bankmanagement.bankmanagementapi.entity.AccountRequest;
import com.bankmanagement.bankmanagementapi.entity.BranchRequest;
import com.bankmanagement.bankmanagementapi.exception.AccountDetailsNotFound;
import com.bankmanagement.bankmanagementapi.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){

        List<AccountDTO> accountDTOList = null;

        try {
            accountDTOList = accountService.findAllAccounts();
            log.info("Accounts List :{}" , accountDTOList);
        }
        catch (AccountDetailsNotFound e){
            log.error("Account Details Not Found" , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception exception){
            log.error("Error in Manipulating Datas " , exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of AccountController.getAllAccounts");
        return new ResponseEntity<>(accountDTOList , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountRequest accountRequest){
        log.info("Inside the AccountController.saveAccount");

        if(Objects.isNull(accountRequest)){
            log.error("No Account Details Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccountDTO accountDTO = null;

        try {
            accountDTO = accountService.saveAccountDetails(accountRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (AccountDetailsNotFound e){
            log.error("Account Details not Found in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error While Manipulating Data" , e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountRequest accountRequest){
        log.info("Inside the AccountController.updateAccount");

        if(Objects.isNull(accountRequest)){
            log.error("Details not Found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AccountDTO accountDTO = null;

        try {
            accountDTO = accountService.updateAccount(accountRequest);
            log.info("Account Details:{}" , accountRequest);
        }
        catch (AccountDetailsNotFound e){
            log.error("Account Details not found " , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error in Manipulating Data ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of AccountController.updateAccount");
        return new ResponseEntity<>(accountDTO , HttpStatus.OK);
    }
}
