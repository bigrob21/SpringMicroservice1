package com.paul.robert.security.jwt;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paul.robert.dto.security.model.AppUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AppUserJWTParser implements JWTPrincipalParser<AppUser> {

	private String secretKey;
	@Getter @Setter
	private long tokenExpirationInMillis = 30000;
	@Getter @Setter
	private ObjectMapper mapper = new ObjectMapper(); 
	
	
	public AppUserJWTParser(String secret){
		this.secretKey = Optional.ofNullable(secret)
			.filter(str -> str != null && !str.trim().equals(""))
			.orElseThrow(() -> new IllegalArgumentException("A secret key must be provided!"));
	}
	
	public AppUserJWTParser(String secret, long tokenExpirationTime){
		this.secretKey = Optional.ofNullable(secret)
			.filter(str -> str != null && !str.trim().equals(""))
			.orElseThrow(() -> new IllegalArgumentException("A secret key must be provided!"));
		this.setTokenExpirationInMillis(tokenExpirationTime);
	}
	
	@Override
	public String createJwtToken(AppUser uzer) {
		final String jwtToken = Jwts.builder()
			.setId(UUID.randomUUID().toString())
			.setSubject(uzer.getSubjectDn())
			.setIssuer("SpringMicroservice1App")
			.claim("user", uzer)
			.setNotBefore(Calendar.getInstance().getTime())
			.signWith(SignatureAlgorithm.HS512, this.secretKey)
			.compact();
		return jwtToken;
	}

	@Override
	public AppUser parseToken(String token) {
		return null;
	}

}