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
	
	private String password;
	private String username;
	private String subjectDn;
	
	@Builder.Default private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
	@Builder.Default private boolean accountNonExpired = true;
	@Builder.Default private boolean accountNonLocked = true;
	@Builder.Default private boolean credentialsNonExpired = true;
	@Builder.Default private boolean enabled = true;
	@Builder.Default private List<String> accesses = new ArrayList<>();

	/**
	 * Helper utility  method on the AppPrincipal Method
	 * @param principal
	 * @return
	 */
	public static List<String> convertAuthoritiesToStringList(AppPrincipal principal) {
		return principal.getAuthorities()
				.stream()
				.map(v -> {
					return ((SimpleGrantedAuthority) v).getAuthority();
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * This is to move the responsibilities of converting a list of String to GrantedAuthorities
	 * on this internal model object.
	 */
	public void convertListOfRolesAndSetToGrantedAuthorities(List<String> authStrings) {
		List<? extends GrantedAuthority> convertedAuthorities = 
				authStrings.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		this.getAuthorities().clear();
		this.setAuthorities(convertedAuthorities);
	}
	
}