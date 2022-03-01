package com.bitbank.utils;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * This class returns current DateTime (or other time).
 * 
 * It is useful for Unit Testing.
 * 
 * @author sineverba
 *
 */
@Component
public class TimeSource implements Serializable {

	@Value("${app.jwtRefreshTokenExpirationMs}")
	private Long refreshTokenDurationMs;

	private static final long serialVersionUID = -20220210203900L;

	/**
	 * Return current time in millis.
	 * 
	 * @return Long current time in millis
	 */
	public Long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * Return a date in the future: the expiry date of Refresh token.
	 * 
	 * @return Instant
	 */
	public Instant getRefreshTokenExpiryDate() {
		return Instant.now().plusMillis(refreshTokenDurationMs);
	}

}
