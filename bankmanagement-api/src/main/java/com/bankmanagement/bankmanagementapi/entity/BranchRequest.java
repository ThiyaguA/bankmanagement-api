package com.bankmanagement.bankmanagementapi.entity;

import lombok.Data;

@Data
public class BranchRequest {

    private String branchName;
    private String branchAddress;
    private int bankCode;

}
