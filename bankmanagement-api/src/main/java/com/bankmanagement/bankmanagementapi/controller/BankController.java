package com.bankmanagement.bankmanagementapi.controller;


import com.bankmanagement.bankmanagementapi.dto.BankDTO;
import com.bankmanagement.bankmanagementapi.entity.BankRequest;
import com.bankmanagement.bankmanagementapi.exception.BankDetailsNotFound;
import com.bankmanagement.bankmanagementapi.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping
    public ResponseEntity<List<BankDTO>> getAllBanks() {

        List<BankDTO> bankDTOList = null;

        try {
            bankDTOList = bankService.findAllBanks();
            log.info("Bank Details :{}", bankDTOList);
        } catch (BankDetailsNotFound e) {
            log.error("Bank Details Not Found", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            log.error("Error during Manipulating Data", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.getAllBanks");
        return new ResponseEntity<>(bankDTOList, HttpStatus.OK);
    }

    @GetMapping("/{bankCode}")
    public ResponseEntity<BankDTO> getDetailsById(@PathVariable int bankCode) {

        log.info("Inside the BankController.getDetailsById, bankCode:{}", bankCode);
        BankDTO bankDTOList = null;

        try {
            bankDTOList = bankService.findBankDetailsById(bankCode);
            log.info("Details As per your ID:{} , Details :{}", bankCode, bankDTOList);
        } catch (BankDetailsNotFound e) {
            log.error("Bank Details Not Found as per Your ID", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error in Manipulating Data ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.getDetailsById");
        return new ResponseEntity<>(bankDTOList, HttpStatus.OK);
    }

    @GetMapping("/getName")
    public ResponseEntity<BankDTO> getAllBanksByName(@RequestParam("name") String bankName){

        log.info("Inside BankServiceImpl.getAllBanksByName");

        if(bankName.isEmpty()){
            log.error("Invalid Input Bank Name:{}",bankName);
        }

        BankDTO bankDTO = null;

        try {
            bankDTO = bankService.getByName(bankName);
        }
        catch (BankDetailsNotFound e){
            log.error("Bank Details Not Found" , e);
        }
        catch (Exception e) {
            log.error("Error in Manipulating Data ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.getAllBanksByName");
        return new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankDTO> saveBank(@RequestBody BankRequest bankRequest){

        log.info("Inside the BankController.saveBank");

        if(Objects.isNull(bankRequest)){
            log.error("Bank Details is Empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BankDTO bankDTO = null;

        try {
            bankDTO = bankService.saveBank(bankRequest);
            log.info("Bank Details Bank DTO:{}",bankRequest);
        }
        catch (BankDetailsNotFound e){
            log.error("Bank Details Not Found",e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error While Manipulating Data" , e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.saveBank");
        return new ResponseEntity<>(bankDTO,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<BankDTO> updateBank(@RequestBody BankRequest bankRequest){
        log.info("Inside the BranchController.updateBank");

        if(Objects.isNull(bankRequest)){
            log.error("Bank Details is Empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BankDTO bankDTO = null;

        try {
            bankDTO = bankService.updateBankDetails(bankRequest);
            log.info("Bank Details Bank DTO:{}",bankRequest);
        }
        catch (BankDetailsNotFound e){
            log.error("No Bank Details Found" , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error in Manipulating data" , e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of BranchController.updateBank");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteBank(@PathVariable("code") int bankCode){
        log.info("Inside the BankController.deleteBank");

        String response = null;

        if(bankCode <= 0){
            log.error("Invalid Bank Code:{}",bankCode);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            response = bankService.deleteBank(bankCode);
            log.info("Response :{}",response);
        }
        catch (BankDetailsNotFound e){
            log.error("Bank Details not deleted ",e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error while deleting Bank data",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of BankController.deleteBank");
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<String> deleteBankByName(@PathVariable("name") String bankName){
        log.info("Inside the BankController.deleteBankByName");

        if(Objects.isNull(bankName)){
            log.error("Invalid Name");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String response = null;

        try {
            response = bankService.deleteBankByName(bankName);
            log.info("Response :{}",response);
        }
        catch (BankDetailsNotFound e){
            log.error("Bank Details not Found",e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Error While manipulating Bank Details",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of BankController.deleteBankByName");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
