package com.paul.robert.security.utils;

import java.security.cert.X509Certificate;
import java.util.Optional;

//import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;
//import org.springframework.security.web.authentication.preauth.x509.X509PrincipalExtractor;

public final class X509PrincipalExtractorUtils {

//	public static final String WHOLE_SUBJECT_DN_LINE_REGEX = "(.*)";
//	public static X509PrincipalExtractor X509WholeSubjectDNPrincipalExtractor;
	
	
	/*private X509PrincipalExtractorUtils() {
		X509WholeSubjectDNPrincipalExtractor = new SubjectDnX509PrincipalExtractor();
		((SubjectDnX509PrincipalExtractor)X509WholeSubjectDNPrincipalExtractor)
			.setSubjectDnRegex(WHOLE_SUBJECT_DN_LINE_REGEX);
	}*/
				
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