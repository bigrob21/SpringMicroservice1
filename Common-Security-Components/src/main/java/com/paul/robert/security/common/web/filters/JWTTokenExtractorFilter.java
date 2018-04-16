package com.paul.robert.security.common.web.filters;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paul.robert.dto.security.model.AppUser;
import com.paul.robert.internal.security.model.AppPrincipal;
import com.paul.robert.security.jwt.JWTPrincipalParser;

public class JWTTokenExtractorFilter extends OncePerRequestFilter {

	private JWTPrincipalParser<?> principalParser;
	
	public JWTTokenExtractorFilter(JWTPrincipalParser<?> parser) {
		Objects.requireNonNull(parser, "Missing required dependency " + parser.getClass());
		this.principalParser = parser;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		final String jwtTokeFromHeader = request.getHeader("Authorization");
		String rawJwtToken = "";
		if(jwtTokeFromHeader != null && !jwtTokeFromHeader.trim().isEmpty()) {
			rawJwtToken = extractToken(jwtTokeFromHeader);
			if(rawJwtToken != null && !rawJwtToken.trim().isEmpty()) {
				AppUser uzer = getAppUserFromToken(rawJwtToken);
				AppPrincipal principal = AppUser.convertAppUserToAppPrincipal(uzer, uzer.getSubjectDn());
				PreAuthenticatedAuthenticationToken authentedToken = 
						new PreAuthenticatedAuthenticationToken(principal, rawJwtToken, principal.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentedToken);
				
				response.setHeader("Authorization", "Bearer: " + rawJwtToken);
				
			}
		}
	}

	private String extractToken(final String token) {
		return token.replaceAll("Bearer", "").replaceAll(":", "");
		
	}
	
	private AppUser getAppUserFromToken(final String rawToken) {
		return (AppUser)this.principalParser.parseToken(rawToken);
	}
	
}