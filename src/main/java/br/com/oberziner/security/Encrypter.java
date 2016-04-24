package br.com.oberziner.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {

	public static final String encryptMD5(String source)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] sourceBytes = source.getBytes();
		md.reset();
		byte[] digested = md.digest(sourceBytes);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digested.length; i++) {
			sb.append(Integer.toHexString(0xff & digested[i]));
		}
		return sb.toString();
	}

}
