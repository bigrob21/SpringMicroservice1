package com.paul.robert.security.jwt;

public interface JWTPrincipalParser <T> {
	String createJwtToken(T t);
	T parseToken(String token);
}
