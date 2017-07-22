package com.wqh.searcher;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class CertificateUtils {
	public static byte[] readCertificates() throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		InputStream in = new FileInputStream("C:/Users/WQH/https.crt");
		Certificate cer = factory.generateCertificate(in);
		return cer.getEncoded();
	}
	
	public static byte[] readPrivateKey() throws Exception {
		KeyStore store = KeyStore.getInstance("JKS");
		InputStream in = new FileInputStream("C:/Users/WQH/https.keystore");
		store.load(in,"wqhuser".toCharArray());
		PrivateKey pk = (PrivateKey)store.getKey("wqhuser", "wqhuser".toCharArray());
		return pk.getEncoded();
	}
	
	public static PrivateKey readPrivateKeys() throws Exception {
		KeyStore store = KeyStore.getInstance("JKS");
		InputStream in = new FileInputStream("C:/Users/WQH/https.keystore");
		store.load(in,"wqhuser".toCharArray());
		PrivateKey pk = (PrivateKey)store.getKey("wqhuser", "wqhuser".toCharArray());
		return pk;
	}
	
	public static PublicKey readPublicKeys() throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		InputStream in = new FileInputStream("C:/Users/WQH/https.crt");
		Certificate crt = factory.generateCertificate(in);
		return crt.getPublicKey();
	}
	
	public static Certificate createCertificate(byte b[]) throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		InputStream in = new ByteArrayInputStream(b);
		Certificate crt = factory.generateCertificate(in);
		return crt;
	}
	
	public static String byte2Hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			}
			else {
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	} 
}
