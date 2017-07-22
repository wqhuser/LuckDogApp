package com.wqh.searcher.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesCoder {
	private static final String KEY_ALGORITHM = "DES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
	private static final String DEFAULT_CIPHER_ALGORITHM1 = "DES/ECB/ISO10126Padding";

	public static byte[] initSecretKey(SecureRandom random) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(random);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	public static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey secretKey = skf.generateSecret(dks);
		return secretKey;
	}

	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	public static byte[] encrypt(byte[] data, Key key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	public static byte[] encrypt(byte[] data, byte[] key,
			String defaultCipherAlgorithm) throws Exception {
		Key k = toKey(key);
		return encrypt(data, k, defaultCipherAlgorithm);
	}

	public static byte[] encrypt(byte[] data, Key k,
			String defaultCipherAlgorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(defaultCipherAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	public static byte[] decrypt(byte[] data, byte[] key,
			String defaultCipherAlgorithm) throws Exception {
		Key k = toKey(key);
		return decrypt(data, k, defaultCipherAlgorithm);
	}

	public static byte[] decrypt(byte[] data, Key k,
			String defaultCipherAlgorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(defaultCipherAlgorithm);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	public static String showByteArray(byte[] data) {
		System.out.println("showByteArray:" + Arrays.toString(data));
		if (null == data) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}
}
