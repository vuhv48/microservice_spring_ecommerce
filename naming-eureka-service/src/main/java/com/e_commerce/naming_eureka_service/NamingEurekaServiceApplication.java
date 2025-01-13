package com.e_commerce.naming_eureka_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NamingEurekaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamingEurekaServiceApplication.class, args);
	}

}
