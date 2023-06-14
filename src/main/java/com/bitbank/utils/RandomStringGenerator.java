package com.bitbank.utils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringGenerator implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Generate a random string of length of 24 chars
	 * 
	 * @return String a random string
	 */
	public String getRandomString() {

		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[24];
		random.nextBytes(bytes);

		byte[] encoded = Base64.getUrlEncoder().encode(bytes);
		return new String(encoded, StandardCharsets.UTF_8);

	}

}
