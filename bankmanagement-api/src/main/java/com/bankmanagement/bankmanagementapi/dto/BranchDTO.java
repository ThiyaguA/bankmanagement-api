package com.bankmanagement.bankmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDTO {

    private int bankCode;
    private String branchName;
    private String branchAddress;

}
