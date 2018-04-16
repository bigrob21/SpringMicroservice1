package com.paul.robert.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 
 * @author Rob Paul Jr
 *
 */
@Data
@ConfigurationProperties(prefix="app.security")
public class AppSecurityConfiguration {
	private long expiration = 300000L;
	private String secret;
}