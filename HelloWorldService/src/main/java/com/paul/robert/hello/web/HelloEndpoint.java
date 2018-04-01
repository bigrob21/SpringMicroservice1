package com.paul.robert.hello.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloEndpoint {

	//TODO: Make sure you remove or default the required flag to true!!
	@GetMapping(path="/")
	public ResponseEntity<String> hello(@RequestHeader(value="X-PROXIED-SUBJECT", required=false) String userSubject){
		return ResponseEntity.ok("Hello There!!!" + userSubject);
	}
	
}
