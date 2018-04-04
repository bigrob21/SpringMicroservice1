package com.paul.robert.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.paul.robert.internal.security.model.AppPrincipal;

@Service
public class UserServiceImpl implements UserService {

	private Set<AppPrincipal> appPrincipalDao = new HashSet<>();

	//CN=Les Harry, L=Anywhere, ST=Maryland, C=US, EMAILADDRESS=lharry@noemail.com
	//CN=Rob Paul, OU=Development, O=BigRob, L=Damascus, ST=Maryland, C=US, EMAILADDRESS=rp@noemail
	public UserServiceImpl() {
		AppPrincipal u1 = AppPrincipal.builder()
			.username("rpaul")
			.subjectDn("CN=Rob Paul, OU=Development, O=BigRob, L=Damascus, ST=Maryland, C=US, EMAILADDRESS=rp@noemail")
			.accesses(Arrays.asList("USER", "ADMIN"))
			.build();
		AppPrincipal u2 = AppPrincipal.builder()
				.username("lessharry")
				.subjectDn("CN=Les Harry, L=Anywhere, ST=Maryland, C=US, EMAILADDRESS=lharry@noemail.com")
				.accesses(Arrays.asList("USER"))
				.build();
		u1.convertListOfRolesAndSetToGrantedAuthorities(u1.getAccesses());
		u2.convertListOfRolesAndSetToGrantedAuthorities(u2.getAccesses());
		appPrincipalDao.addAll(Arrays.asList(u1, u2));
	}
	
	@Override
	public AppPrincipal lookupUserBySubjectDn(String subjectDn) {
		return appPrincipalDao.stream()
			.filter(p -> p.getSubjectDn().equals(subjectDn))
			.findFirst()
			.orElse(null);
	}

	@Override
	public AppPrincipal lookupUserByUsername(String userName) {
		return appPrincipalDao.stream()
				.filter(p -> p.getUsername().equals(userName))
				.findFirst()
				.orElse(null);
	}

}