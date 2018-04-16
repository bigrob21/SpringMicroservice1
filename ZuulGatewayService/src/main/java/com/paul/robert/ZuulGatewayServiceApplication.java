package com.paul.robert;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.paul.robert.security.config.AppSecurityConfiguration;
import com.paul.robert.security.jwt.AppUserJWTParser;
import com.paul.robert.security.jwt.JWTPrincipalParser;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"com.paul.robert.feign.client"})
@ComponentScans(value={
		@ComponentScan("com.paul.robert.zuul.filter")
})
public class ZuulGatewayServiceApplication {

	@Autowired
	private AppSecurityConfiguration secConfig;
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulGatewayServiceApplication.class, args);
	}
	
	//Configure CORS through the Edge Service
	@Bean
	public FilterRegistrationBean corsFilter() { 
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    source.registerCorsConfiguration("/**", config);
	    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	    bean.setOrder(0);
	    return bean;
	}
	
	@Bean
	public JWTPrincipalParser<?> principalParser(){
		Objects.requireNonNull(secConfig, "Security Config is required to run!!!");
		return new AppUserJWTParser(secConfig.getSecret(), secConfig.getExpiration());
	}
}