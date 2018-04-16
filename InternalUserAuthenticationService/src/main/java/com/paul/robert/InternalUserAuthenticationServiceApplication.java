package com.paul.robert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.paul.robert.security.common.web.config.AppCommonWebConfiguration;
import com.paul.robert.security.config.AppSecurityConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(value= {AppSecurityConfiguration.class})
@Import(value= {AppCommonWebConfiguration.class})
public class InternalUserAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternalUserAuthenticationServiceApplication.class, args);
	}
	
	//TODO: Remove this once the Web Filter framework is ready.
	/*@Bean
	public GenericFilterBean subjectHeaderFilterBean() {
		return new ProxiedSubjectHeaderPersistenceFilter();
	}*/
	
}
