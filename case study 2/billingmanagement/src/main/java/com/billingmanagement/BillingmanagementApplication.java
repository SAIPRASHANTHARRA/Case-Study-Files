package com.billingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BillingmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingmanagementApplication.class, args);
	}

}
