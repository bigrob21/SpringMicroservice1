package com.paul.robert.security.common.web.config;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import com.paul.robert.security.common.web.filters.JWTTokenExtractorFilter;
import com.paul.robert.security.common.web.filters.ProxiedSubjectHeaderPersistenceFilter;
import com.paul.robert.security.config.AppSecurityConfiguration;
import com.paul.robert.security.jwt.AppUserJWTParser;
import com.paul.robert.security.jwt.JWTPrincipalParser;

@EnableWebSecurity
public class AppCommonWebConfiguration extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private Environment env;
	
	@Autowired
	private AppSecurityConfiguration secConfig;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(subjectHeaderFilterBean(), JWTTokenExtractorFilter.class)
		.authorizeRequests()
		.antMatchers("/**")
		.authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.csrf()
		.disable();
	}

	@Bean
	public GenericFilterBean jwtFilter() {
		return new JWTTokenExtractorFilter(principalParser());
	}
	
	@Bean
	public GenericFilterBean subjectHeaderFilterBean() {
		return new ProxiedSubjectHeaderPersistenceFilter();
	}
	
	@Bean
	public JWTPrincipalParser<?> principalParser(){
		Objects.requireNonNull(secConfig, "Security Config is required to run!!!");
		return new AppUserJWTParser(secConfig.getSecret(), secConfig.getExpiration());
	}
	
}