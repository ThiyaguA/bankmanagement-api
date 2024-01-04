package com.bankmanagement.bankmanagementapi.repository;

import com.bankmanagement.bankmanagementapi.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepo extends JpaRepository <Loan, Integer> {
}
