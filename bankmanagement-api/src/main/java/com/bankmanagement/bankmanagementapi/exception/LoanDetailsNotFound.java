package com.bankmanagement.bankmanagementapi.exception;

public class LoanDetailsNotFound extends Exception {

    public LoanDetailsNotFound(){
        super();
    }

    public LoanDetailsNotFound(String msg){
        super(msg);
    }

    public LoanDetailsNotFound(String msg , Throwable throwable){
        super(msg,throwable);
    }
}
