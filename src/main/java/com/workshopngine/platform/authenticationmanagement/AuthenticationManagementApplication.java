package com.workshopngine.platform.authenticationmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AuthenticationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationManagementApplication.class, args);
	}

}
