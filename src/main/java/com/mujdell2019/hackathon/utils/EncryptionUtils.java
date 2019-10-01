package com.mujdell2019.hackathon.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
@Scope("singleton")
public class EncryptionUtils {

	@Value("${application.encryption.iv}")
	private String encryptionIv;
	
	@Value("${application.encryption.key}")
	private String encryptionKey;
	
	/*
	 * member function to encrypt a plain text
	 * */
	public String encrypt(String plainText) {
		try {
			IvParameterSpec iv = new IvParameterSpec(encryptionIv.getBytes("UTF-8"));
			SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			
			byte[] encrypted = cipher.doFinal(plainText.getBytes());
			
			return Base64.encodeBase64String(encrypted);
			
		} catch (Exception e) {}
	
		return null;
	}
	
	/*
	 * member function to decrypt an encrypted text
	 * */
	public String decrypt(String encryptedText) {
		try {
			IvParameterSpec iv = new IvParameterSpec(encryptionIv.getBytes("UTF-8"));
			SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			
			byte[] plainText = cipher.doFinal(Base64.decodeBase64(encryptedText));
			
			return new String(plainText);
			
		} catch (Exception e) {}
		
		return null;
	}

	/*
	 * member function to hash a plain text, original text cannot be obtained once hashed
	 * */
	public String hash(String plainText) {
		return DigestUtils.md5DigestAsHex(plainText.getBytes());
	}
	
	/*
	 * member function to compare a hashed text with a plain text
	 * */
	public boolean compareHash(String hash, String plainText) {
		return hash.equals(hash(plainText));
	}
}
