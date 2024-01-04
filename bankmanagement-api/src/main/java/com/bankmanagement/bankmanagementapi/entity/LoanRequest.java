package com.bankmanagement.bankmanagementapi.entity;

import lombok.Data;

@Data
public class LoanRequest {

    private int loanId;
    private String loanType;
    private double loanAmount;
    private int cusId;

}
