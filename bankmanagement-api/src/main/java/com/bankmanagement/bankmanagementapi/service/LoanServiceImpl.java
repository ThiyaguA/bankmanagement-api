package com.bankmanagement.bankmanagementapi.service;

import com.bankmanagement.bankmanagementapi.dto.LoanDTO;
import com.bankmanagement.bankmanagementapi.entity.Customer;
import com.bankmanagement.bankmanagementapi.entity.Loan;
import com.bankmanagement.bankmanagementapi.entity.LoanRequest;
import com.bankmanagement.bankmanagementapi.exception.LoanDetailsNotFound;
import com.bankmanagement.bankmanagementapi.repository.CustomerRepo;
import com.bankmanagement.bankmanagementapi.repository.LoanRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepo loanRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<LoanDTO> findAllLoans() throws LoanDetailsNotFound {

        log.info("Inside LoanServiceImpl.findAllLoans");

        List<Loan> loanList = loanRepo.findAll();

        if(CollectionUtils.isEmpty(loanList)){
            log.error("Loan Details Not Found");
            throw new LoanDetailsNotFound("Loan Details Not Found");
        }

        List<LoanDTO> loanDTOList = loanList.stream().map(loan -> {
            LoanDTO loanDTO = new LoanDTO();
            loanDTO.setLoanAmount(loan.getLoanAmount());
            loanDTO.setLoanType(loan.getLoanType());

            return loanDTO;
        }).collect(Collectors.toList());

        log.info("End of LoanServiceImpl.findAllLoans");
        return loanDTOList;
    }

    @Override
    public LoanDTO addDetails(LoanRequest loanRequest) throws LoanDetailsNotFound {
        log.info("Inside the LoanServiceImpl.addDetails");

        if(Objects.isNull(loanRequest)){
            log.error("Loan Details are not Saved");
            throw new LoanDetailsNotFound("Loan Details are not Saved");
        }

        Loan loan = new Loan();
        loan.setLoanAmount(loanRequest.getLoanAmount());
        loan.setLoanType(loanRequest.getLoanType());

        Customer customer = customerRepo.findById(loanRequest.getCusId()).get();

        loan.setCustomer(customer);

        Loan loan1 = loanRepo.save(loan);

        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setLoanAmount(loan1.getLoanAmount());
        loanDTO.setLoanType(loan1.getLoanType());
        loanDTO.setCusId(loan1.getCustomer().getCusId());

        log.info("End of LoanServiceImpl.addDetails");
        return loanDTO;
    }

    @Override
    public LoanDTO updateLoan(LoanRequest loanRequest) throws LoanDetailsNotFound {
        log.info("Inside the LoanServiceImpl.updateLoan");

        Loan loan = loanRepo.findById(loanRequest.getLoanId()).get();

        if(!Objects.isNull(loanRequest.getLoanAmount())){
            loan.setLoanAmount(loanRequest.getLoanAmount());
        }
        if(!Objects.isNull(loanRequest.getLoanType())){
            loan.setLoanType(loanRequest.getLoanType());
        }
        Customer customer = customerRepo.findById(loanRequest.getCusId()).get();

        if(!Objects.isNull(customer)){
            loan.setCustomer(customer);
        }

        Loan loan1 = loanRepo.save(loan);

        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setLoanAmount(loan1.getLoanAmount());
        loanDTO.setLoanType(loan1.getLoanType());
        loanDTO.setCusId(loan1.getCustomer().getCusId());

        log.info("End of LoanServiceImpl.updateLoan");
        return loanDTO;
    }
}
