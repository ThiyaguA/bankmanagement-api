package com.bankmanagement.bankmanagementapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Loan implements Serializable {

    public static final long serial = 543565;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private int loanId;
    private String loanType;
    private double loanAmount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cus_id")
    private Customer customer;
}
