package com.bankmanagement.bankmanagementapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
public class Branch implements Serializable {

    public static final long serial = 543563;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private int branchId;
    private String branchName;
    private String branchAddress;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_code")
    private Bank bank;

    @OneToMany(mappedBy = "branch" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private Set<Account> account;

}
