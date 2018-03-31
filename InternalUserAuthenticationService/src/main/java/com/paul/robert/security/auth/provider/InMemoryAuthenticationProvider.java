package com.paul.robert.security.auth.provider;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.paul.robert.internal.security.model.AppPrincipal;

public class InMemoryAuthenticationProvider implements AuthenticationProvider {

	private Set<AppPrincipal> uzerList = new HashSet<>();
	
	public InMemoryAuthenticationProvider() {
		
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(AppPrincipal.class);
	}

}
