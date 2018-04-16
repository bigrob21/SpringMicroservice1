package com.paul.robert.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paul.robert.dto.security.model.AppUser;

@FeignClient(name="authentication-user-lookupservice", url="http://authentication-service/auth/")
public interface AuthenticationService {

	@RequestMapping(value="/")
	public AppUser lookupUserbySubjectDn(@RequestHeader(value="X-PROXIED-SUBJECT") String subject); 

}
