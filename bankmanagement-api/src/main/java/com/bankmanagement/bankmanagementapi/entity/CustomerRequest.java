package com.bankmanagement.bankmanagementapi.entity;

import lombok.Data;

@Data
public class CustomerRequest {

    private int cusId;
    private String cusAddress;
    private String cusName;
    private String cusPhone;
}
