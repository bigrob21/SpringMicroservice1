package com.paul.robert.internal.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppPrincipal implements UserDetails {

	private static final long serialVersionUID = 9098152614590113044L;

	@Builder.Default  
	private Collection<GrantedAuthority> authorities = new ArrayList<>();
	private String password;
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	@Builder.Default 
	private List<String> accesses = new ArrayList<>();
	
	public static List<String> convertAuthoritiesToStringList(AppPrincipal principal) {
		return principal.getAuthorities()
				.stream()
				.map(v -> {
					return ((SimpleGrantedAuthority) v).getAuthority();
				})
				.collect(Collectors.toList());
	}
	
}