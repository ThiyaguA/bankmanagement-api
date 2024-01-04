package com.bankmanagement.bankmanagementapi.entity;

import com.bankmanagement.bankmanagementapi.dto.BranchDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BankRequest {

    private int bankCode;
    private String bankName;
    private String bankAddress;
}
