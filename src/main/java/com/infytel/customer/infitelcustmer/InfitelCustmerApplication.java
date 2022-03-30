package com.infytel.customer.infitelcustmer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.infytel.customer.infitelcustmer.*")
@EnableDiscoveryClient
public class InfitelCustmerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfitelCustmerApplication.class, args);
	}

}
