package com.bankmanagement.bankmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestBankManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(BankManagementApiApplication::main).with(TestBankManagementApiApplication.class).run(args);
	}

}
