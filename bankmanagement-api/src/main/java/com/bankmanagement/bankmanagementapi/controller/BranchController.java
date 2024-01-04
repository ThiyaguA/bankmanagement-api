package com.bankmanagement.bankmanagementapi.controller;

import com.bankmanagement.bankmanagementapi.dto.BankDTO;
import com.bankmanagement.bankmanagementapi.dto.BranchDTO;
import com.bankmanagement.bankmanagementapi.entity.BankRequest;
import com.bankmanagement.bankmanagementapi.entity.BranchRequest;
import com.bankmanagement.bankmanagementapi.exception.BranchNotFound;
import com.bankmanagement.bankmanagementapi.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("api/v1/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches(){

        List<BranchDTO> branchDTOList = null;

        try {
            branchDTOList = branchService.findAllBranches();
            log.info("Branch Details :{}" , branchDTOList);
        }
        catch (BranchNotFound e){
            log.error("Branch Not Found " , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception exception){
            log.error("Error during Manipulating Data" ,exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(branchDTOList,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BranchDTO> saveBranch(@RequestBody BranchRequest branchRequest){
        log.info("Inside the BranchController.saveBranch");

        if(Objects.isNull(branchRequest)){
            log.error("Branch Details is Empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BranchDTO branchDTOList = null;

        try {
            branchDTOList = branchService.saveAllBranches(branchRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (BranchNotFound e){
            log.error("Branch Details Not found" , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error during Manipulating Data" ,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<BranchDTO> updateBranch(@RequestBody BranchRequest branchRequest){

        log.info("Inside the BranchController.updateBranch");

        BranchDTO branchDTO = null;

        try {
            branchDTO = branchService.updateBranch(branchRequest);
            log.info("Branch Details:{}",branchRequest);
        }
        catch (BranchNotFound e){
            log.error("Branch Details Not Found" , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error While Manipulating Data ",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of BranchController.updateBranch");
        return new ResponseEntity<>(branchDTO,HttpStatus.OK);
    }
}
