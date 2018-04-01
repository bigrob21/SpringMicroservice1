package com.paul.robert.security.utils;

import java.security.cert.X509Certificate;
import java.util.Optional;

public final class X509PrincipalExtractorUtils {

	/**
	 * Helper method that will extract the whole SubjectDN line the assumption is that the extraction
	 * is done on the passed in certificate.
	 * 
	 * @param cert
	 * @return
	 */
	public static String extractSubjectDnLine(X509Certificate cert) {
		if(cert == null) {
			return null;
		}
		return Optional.ofNullable(cert)
			.map(ct -> ct.getSubjectDN())
			.map(x -> x.getName())
			.orElse(null);
	}
	
}