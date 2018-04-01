package com.paul.robert.dto.security.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.toList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.paul.robert.internal.security.model.AppPrincipal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

	private String userName;
	private String subjectDn;
	
	@Builder.Default 
	private List<String> accesses = new ArrayList<>();
	@Builder.Default
	private List<String> roles = new ArrayList<>();
	
	public static AppUser convertDomainToDTO(final AppPrincipal appUser) {
		if(appUser == null) {
			return null;
		}
		return AppUser.builder()
			.userName(appUser.getUsername())
			.roles(AppPrincipal.convertAuthoritiesToStringList(appUser))
			.accesses(appUser.getAccesses())
			.build();
	}

	/**
	 * This method was not designed to get the subjectDN from an X509 and must rely on some client to pass it
	 * to this method.
	 * 
	 * @param appUser
	 * @param subjectDn
	 * @return
	 */
	public static AppPrincipal convertAppUserToAppPrincipal(AppUser appUser, String subjectDn) {
		Objects.requireNonNull(appUser, "Unable to convert a Null " + 
				AppUser.class.getSimpleName() + " to an instance of " + AppPrincipal.class.getSimpleName());
		return AppPrincipal.builder()
			.accesses(appUser.getAccesses())
			.authorities(appUser.getRoles().stream().map(SimpleGrantedAuthority::new).collect(toList()))
			.subjectDn(subjectDn)
			.build();
	}
}