package com.bankmanagement.bankmanagementapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
public class Bank implements Serializable {

    public static final long serial = 543562;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_code")
    private int bankCode;
    private String bankName;
    private String bankAddress;

    @OneToMany(mappedBy = "bank" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private Set<Branch> branch;
}
