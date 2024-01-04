package com.bankmanagement.bankmanagementapi.dto;

import lombok.Data;

@Data
public class LoanDTO {

    private String loanType;
    private double loanAmount;
    private int cusId;

}
