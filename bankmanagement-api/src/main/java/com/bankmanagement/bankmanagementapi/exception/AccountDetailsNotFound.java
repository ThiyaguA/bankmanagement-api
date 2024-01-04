package com.bankmanagement.bankmanagementapi.exception;

public class AccountDetailsNotFound extends Exception {

    public AccountDetailsNotFound(){
        super();
    }

    public AccountDetailsNotFound(String msg){
        super(msg);
    }

    public AccountDetailsNotFound(String msg , Throwable throwable){
        super(msg,throwable);
    }
}
