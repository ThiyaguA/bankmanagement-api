package com.bankmanagement.bankmanagementapi.entity;

import lombok.Data;

@Data
public class AccountRequest {

    private long accountNumber;
    private String accountType;
    private double accountBalance;
    private int branchId;
    private int customerId;

}
