package com.bankmanagement.bankmanagementapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankDTO {

    private int bankCode;
    private String bankName;
    private String bankAddress;
    private List<BranchDTO> branchDto;

}
