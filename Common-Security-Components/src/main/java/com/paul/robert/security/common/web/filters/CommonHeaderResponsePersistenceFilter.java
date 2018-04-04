package com.paul.robert.security.common.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CommonHeaderResponsePersistenceFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		if(request.getHeader("X-PROXIED-SUBJECT") != null) {
			response.setHeader("X-PROXIED-SUBJECT", request.getHeader("X-PROXIED-SUBJECT"));
		}
		filterChain.doFilter(request, response);
	}

}
