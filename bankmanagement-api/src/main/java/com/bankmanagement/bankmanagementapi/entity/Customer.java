package com.bankmanagement.bankmanagementapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
public class Customer implements Serializable {

    public static final long serial = 543564;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cus_id")
    private int cusId;
    private String cusName;
    private String cusPhone;
    private String cusAddress;

    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private Set<Account> account;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Loan> loans;
}
