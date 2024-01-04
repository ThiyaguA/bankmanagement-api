package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.BranchDTO;
import com.bankmanagement.bankmanagementapi.entity.BranchRequest;
import com.bankmanagement.bankmanagementapi.exception.BranchNotFound;

import java.util.List;

public interface BranchService {

    List<BranchDTO> findAllBranches() throws BranchNotFound;

    BranchDTO saveAllBranches(BranchRequest branchRequest) throws BranchNotFound;

    BranchDTO updateBranch(BranchRequest branchRequest) throws BranchNotFound;
}
