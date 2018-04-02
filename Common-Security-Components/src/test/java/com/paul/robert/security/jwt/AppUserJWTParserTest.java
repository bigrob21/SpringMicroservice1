package com.paul.robert.security.jwt;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.paul.robert.dto.security.model.AppUser;
import com.paul.robert.internal.security.model.AppPrincipal;

import io.jsonwebtoken.lang.Assert;

public class AppUserJWTParserTest {

	static AppUserJWTParser jwtUtil;
	
	static final String SECRETKEY = "AbbraC@ddabrA";
	
	@BeforeClass
	public static void setup() {
		AppUserJWTParserTest.jwtUtil = new AppUserJWTParser(SECRETKEY);
	}
	
	@Test
	public void testJwtCreationFromAppUser() {
		AppPrincipal principal =AppPrincipal.builder()
			.accesses(Arrays.asList("Opening", "Closing"))
			.subjectDn("AA BB CC")
			.username("RobP")
			.build();
		//Lets Add Some system roles. 
		principal.convertListOfRolesAndSetToGrantedAuthorities(Arrays.asList("SUPERVISOR", "PEEON"));
		AppUser dto = AppUser.convertDomainToDTO(principal);
		System.out.println(principal);
		System.out.println();
		System.out.println(dto);
		System.out.println();
		final String jwtToken = jwtUtil.createJwtToken(dto);
		System.out.println(jwtToken);
		Assert.notNull(jwtToken);
	}
	
}
