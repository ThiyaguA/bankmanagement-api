package com.bankmanagement.bankmanagementapi.repository;

import com.bankmanagement.bankmanagementapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository <Account, Long> {
}
