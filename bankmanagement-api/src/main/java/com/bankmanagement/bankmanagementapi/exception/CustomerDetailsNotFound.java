package com.bankmanagement.bankmanagementapi.exception;

public class CustomerDetailsNotFound extends Exception {

    public CustomerDetailsNotFound(){
        super();
    }

    public CustomerDetailsNotFound(String msg){
        super(msg);
    }

    public CustomerDetailsNotFound(String msg , Throwable throwable){
        super(msg,throwable);
    }
}
