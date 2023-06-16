package com.bitbank.services;

import org.jboss.aerogear.security.otp.Totp;
import org.springframework.stereotype.Service;

@Service
public class MfaService {

	private static final long serialVersionUID = -202306151424001L;

	// The shared secret of the user
	private String secret;

	public void setSecret(String secret) {
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
