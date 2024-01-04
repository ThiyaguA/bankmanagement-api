package com.bankmanagement.bankmanagementapi.dto;

import lombok.Data;

@Data
public class AccountDTO {

    private long accountNumber;
    private String accountType;
    private double accountBalance;
    private int branchId;
    private int customerId;
}
