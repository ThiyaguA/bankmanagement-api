package com.bankmanagement.bankmanagementapi.exception;

public class BranchNotFound extends Exception{

    public BranchNotFound(){
        super();
    }

    public BranchNotFound(String msg){
        super(msg);
    }

    public BranchNotFound(Throwable throwable , String msg){
        super(msg , throwable);
    }
}
