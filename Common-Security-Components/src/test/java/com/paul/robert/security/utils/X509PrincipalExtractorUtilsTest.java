package com.paul.robert.security.utils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class X509PrincipalExtractorUtilsTest {

	public static X509Certificate testCertificate;
	
	static final String TEST_CERT_CLASSPATH_LOCATION = "/tester.jks";
	static final String EXPECTED_SUBJECT_DN_LINE = "CN=junit tester, ST=test, EMAILADDRESS=test@nonvalid.email";
	static final String JKS_FILE_TYPE = "JKS";
	//Yes that is correct this test cert has no password!
	static final String KEYSTORE_PASSWORD = "";
	
	@BeforeClass
	public static void loadTestCertificateFromClasspath() {
		X509Certificate x509Cert = null;
		try {
			InputStream in = X509PrincipalExtractorUtilsTest.class.getResourceAsStream(TEST_CERT_CLASSPATH_LOCATION);
			KeyStore keyStore = KeyStore.getInstance(JKS_FILE_TYPE);
			keyStore.load(in, KEYSTORE_PASSWORD.toCharArray());
			Certificate[] certificateChain = keyStore.getCertificateChain("junit tester");
			x509Cert = (X509Certificate) certificateChain[0];
		}catch(Throwable t) {
			throw new RuntimeException("Unable to find the certificate for testing. Test should fail! ", t);
		}
		X509PrincipalExtractorUtilsTest.testCertificate =  x509Cert;
	}
	
	@Test
	public void testCertSubjectDNExtraction() {
		Assert.assertTrue(X509PrincipalExtractorUtils.extractSubjectDnLine(testCertificate).equals(EXPECTED_SUBJECT_DN_LINE));
	}
	
	@Test
	public void testNoCertYieldsNullValue() {
		Assert.assertNull(X509PrincipalExtractorUtils.extractSubjectDnLine(null));
	}
	
}