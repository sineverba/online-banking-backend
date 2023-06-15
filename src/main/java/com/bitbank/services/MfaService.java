package com.bitbank.services;

import java.io.Serializable;

import org.jboss.aerogear.security.otp.Totp;

public class MfaService implements Serializable {

	private static final long serialVersionUID = -202306151424001L;

	public MfaService(String secret) {
		this.setSecret(secret);
	}

	// The shared secret of the user
	private String secret;

	private void setSecret(String secret) {
		this.secret = secret;
	}

	private String getSecret() {
		return this.secret;
	}

	public boolean verify(String mfaCode) {
		Totp totp = new Totp(this.getSecret());
		return totp.verify(mfaCode);
	}
}
