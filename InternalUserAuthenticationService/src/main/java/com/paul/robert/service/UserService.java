package com.paul.robert.service;

import javax.validation.constraints.NotNull;

import com.paul.robert.internal.security.model.AppPrincipal;

public interface UserService {
	public AppPrincipal lookupUserBySubjectDn(@NotNull String subjectDn);
	public AppPrincipal lookupUserByUsername(@NotNull String userName);
}
