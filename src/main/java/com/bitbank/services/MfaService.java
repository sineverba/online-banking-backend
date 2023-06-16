package com.bitbank.services;

import org.springframework.stereotype.Service;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;

@Service
public class MfaService {

	/**
	 * Generate a random secret to store into user data. E.g.:
	 * "BP26TDZUZ5SVPZJRIHCAUVREO5EWMHHV"
	 * 
	 * @return String a random secret
	 */
	public String generateSecret() {
		SecretGenerator secretGenerator = new DefaultSecretGenerator();
		return secretGenerator.generate();
	}

	public boolean verify(String sharedSecret, String mfaCode) {
		TimeProvider timeProvider = new SystemTimeProvider();
		CodeGenerator codeGenerator = new DefaultCodeGenerator();

		CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

		// secret = the shared secret for the user
		// code = the code submitted by the user
		return verifier.isValidCode(sharedSecret, mfaCode);
	}
}
