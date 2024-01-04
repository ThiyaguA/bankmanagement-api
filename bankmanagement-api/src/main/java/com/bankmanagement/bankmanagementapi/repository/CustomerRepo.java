package com.bankmanagement.bankmanagementapi.repository;

import com.bankmanagement.bankmanagementapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository <Customer, Integer> {
}
