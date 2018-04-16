package com.paul.robert.zuul.filter;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.paul.robert.dto.security.model.AppUser;
import com.paul.robert.feign.client.AuthenticationService;
import com.paul.robert.security.jwt.JWTPrincipalParser;
import com.paul.robert.security.utils.X509PrincipalExtractorUtils;

@Component("X509PrefilterReaderFilter")
public class X509ExtractorFilter extends ZuulFilter {

	static final String SERVLET_X509_KEY = "javax.servlet.request.X509Certificate";
	static final String GLOBAL_PROXIED_SUBJECT_HEADER_KEY = "X-PROXIED-SUBJECT";
	
	@Autowired
	private AuthenticationService authService;
	@Autowired
	private JWTPrincipalParser<?> jwtParser;
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = RequestContext.getCurrentContext()
			.getRequest();
		X509Certificate certs[] = 
			    (X509Certificate[])request
			    	.getAttribute(SERVLET_X509_KEY);
		final String subjectDn = X509PrincipalExtractorUtils.extractSubjectDnLine(certs[0]);
		ctx.addZuulRequestHeader(GLOBAL_PROXIED_SUBJECT_HEADER_KEY, subjectDn);

		//
		if(!RequestContext.getCurrentContext().getZuulRequestHeaders().containsKey("Authorization")) {
			//TODO: Make sure that the JWT is still Valid
			AppUser uzer = authService.lookupUserbySubjectDn(subjectDn);
			
		}
		
		//TODO: Do not forget to get and set the JWT once you are ready!!!
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
