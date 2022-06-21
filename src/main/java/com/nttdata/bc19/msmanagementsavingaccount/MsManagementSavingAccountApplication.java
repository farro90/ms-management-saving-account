package com.nttdata.bc19.msmanagementsavingaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsManagementSavingAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsManagementSavingAccountApplication.class, args);
	}

}
