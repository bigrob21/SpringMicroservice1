package com.paul.robert.dto.security.model;

import java.util.ArrayList;
import java.util.List;

import com.paul.robert.internal.security.model.AppPrincipal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDTO {

	private String userName;
	@Builder.Default 
	private List<String> accesses = new ArrayList<>();
	@Builder.Default
	private List<String> roles = new ArrayList<>();
	
	public static AppUserDTO convertDomainToDTO(final AppPrincipal appUser) {
		if(appUser == null) {
			return null;
		}
		return AppUserDTO.builder()
			.userName(appUser.getUsername())
			.roles(AppPrincipal.convertAuthoritiesToStringList(appUser))
			.accesses(appUser.getAccesses())
			.build();
	}

/*	public static void main(String[] args) {
		AppPrincipal principal = AppPrincipal.builder()
			.username("rmpaul")
			.authorities(Arrays.asList(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")))
			.accesses(Arrays.asList("TOP SUSHI", "FUGU", "CALAMARI"))
			.build();
		System.out.println(AppUserDTO.convertDomainToDTO(principal));
	}*/
	
}