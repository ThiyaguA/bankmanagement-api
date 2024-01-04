package com.bankmanagement.bankmanagementapi.repository;

import com.bankmanagement.bankmanagementapi.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepo extends JpaRepository <Branch, Integer> {

}
