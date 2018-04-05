package com.paul.robert.security.jwt;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.paul.robert.dto.security.model.AppUser;
import com.paul.robert.internal.security.model.AppPrincipal;

public class AppUserJWTParserTest {

	static AppUserJWTParser jwtUtil;

	static final String SECRETKEY = "AbbraC@ddabrA";

	@BeforeClass
	public static void setup() {
		AppUserJWTParserTest.jwtUtil = new AppUserJWTParser(SECRETKEY);
	}

	@Test
	public void testJwtCreationFromAppUser() {
		AppUser dto = generateAppUser();
		final String jwtToken = jwtUtil.createJwtToken(dto);
		System.out.println(jwtToken);
		Assert.assertNotNull(jwtToken);
	}

	@Test
	public void testJWTDecodeToPricipalDTO() {
		AppUser dto = generateAppUser();
		final String jwtToken = AppUserJWTParserTest.jwtUtil.createJwtToken(dto);
		AppUser uzer = AppUserJWTParserTest.jwtUtil.parseToken(jwtToken);
		Assert.assertEquals(dto, uzer);
	}

	private AppUser generateAppUser() {
		AppPrincipal principal = AppPrincipal.builder()
				.accesses(Arrays.asList("Opening", "Closing"))
				.subjectDn("AA BB CC").username("RobP")
				.build();
		// Lets Add Some system roles.
		principal.convertListOfRolesAndSetToGrantedAuthorities(Arrays.asList("SUPERVISOR", "PEEON"));
		AppUser dto = AppUser.convertDomainToDTO(principal);
		return dto;
	}
}
