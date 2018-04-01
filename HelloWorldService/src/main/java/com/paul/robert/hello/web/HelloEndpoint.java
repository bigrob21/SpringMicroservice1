package com.paul.robert.hello.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloEndpoint {

	@GetMapping(path="/")
	public ResponseEntity<String> hello(){
		return ResponseEntity.ok("Hello There!!!");
	}
	
}
