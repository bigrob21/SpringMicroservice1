package com.paul.robert.web;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.paul.robert.dto.security.model.AppUser;
import com.paul.robert.internal.security.model.AppPrincipal;
import com.paul.robert.service.UserService;

@RestController
public class AuthenticationController {

	private UserService userService;

	public AuthenticationController(@NotNull UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	public ResponseEntity<AppUser> lookupUserbySubjectDn(@RequestHeader(value="X-PROXIED-SUBJECT") String subject){
		AppPrincipal p = userService.lookupUserBySubjectDn(subject);
		ResponseEntity<AppUser> resp = Optional.ofNullable(p)
			.map(uzr -> {
				return AppUser.convertDomainToDTO(p);
			})
			.filter(au -> au != null)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		return resp;
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> testExtractUserSubjectFromHeader(@RequestHeader(value="X-PROXIED-SUBJECT", required=false) String subject){
		ResponseEntity<String> resp = Optional.ofNullable(subject)
			.filter(s-> s != null && !s.trim().equals(""))
			.map(s -> { return ResponseEntity.ok(s); })
			.orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("-- NO HEADER CONTENT --"));
		return resp;
	}
	
}