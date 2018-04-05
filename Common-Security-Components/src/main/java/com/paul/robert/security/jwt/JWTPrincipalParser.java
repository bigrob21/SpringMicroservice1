package com.paul.robert.security.jwt;

import java.util.Base64;

public interface JWTPrincipalParser <T> {
	
	static final String JWT_PARSE_REGEX = "\\.";
	
	String createJwtToken(T t);
	T parseToken(String token);
	
	/**
	 * Generalized operation that should be available to all implementation to allow implementers the ability
	 * to extract a JWT token for the body.
	 * 
	 * @param jwtToken
	 * @return
	 */
	default String extractPayloadAsJsonFromJwtTokenFromBody(final String jwtToken) {
		String[] tokens = jwtToken.split(JWT_PARSE_REGEX);
		if(tokens.length != 3) {
			throw new IllegalArgumentException("Unable to parse presented JWT token - " + jwtToken);
		}
		String body = tokens[1];
		return new String(Base64.getDecoder().decode(body));
	}
}
