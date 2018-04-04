package com.paul.robert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.GenericFilterBean;

import com.paul.robert.security.common.web.filters.CommonHeaderResponsePersistenceFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class InternalUserAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternalUserAuthenticationServiceApplication.class, args);
	}
	
	//TODO: Remove this once the Web Filter framework is ready.
	@Bean
	public GenericFilterBean subjectHeaderFilterBean() {
		return new CommonHeaderResponsePersistenceFilter();
	}
	
}
