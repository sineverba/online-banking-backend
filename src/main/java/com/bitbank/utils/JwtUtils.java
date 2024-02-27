package com.bitbank.utils;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.bitbank.entities.RolesEntity;
import com.bitbank.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Autowired
	private TimeSource timeSource;

	/**
	 * Generate the Key from the string.
	 * 
	 * @return
	 */
	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	/**
	 * Generate the JWT token from an Authentication. If you have a String token,
	 * use `generateJwtToken(String token)`
	 * 
	 * @param authentication
	 * @return The JWT
	 */
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		return this.getJwt(userPrincipal.getUsername());
	}

	/**
	 * Generate the JWT token from a String. If you have an Authentication, use
	 * `generateJwtToken(Authentication authentication)`
	 * 
	 * @param authentication
	 * @return The JWT
	 */
	public String generateJwtToken(String token) {
		return this.getJwt(this.getUserNameFromJwtToken(token));
	}

	/**
	 * Generate the JWT token from username
	 * 
	 * @param authentication
	 * @return The JWT
	 */
	public String generateJwtTokenFromUsername(String username) {
		return this.getJwt(username);
	}

	/**
	 * 
	 * Get the username from a token.
	 * 
	 * @param token
	 * @return the username
	 */
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	/**
	 * Get the expiry date from a token
	 * 
	 * @param token
	 * @return the expiry date
	 */
	public Long getExpiryDateFromJwtToken(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getExpiration()
				.getTime();
	}

	/**
	 * Validate a token.
	 * 
	 * @param authToken
	 * @return
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(authToken);
			return true;
		} catch (MalformedJwtException | ExpiredJwtException e) {
			return false;
		}
	}

	/**
	 * Create a jwt starting from an username.
	 * 
	 * @param username
	 * @return
	 */
	private String getJwt(String username) {
		return Jwts.builder().subject(username).issuedAt(new Date(timeSource.getCurrentTimeMillis()))
				.expiration(new Date(timeSource.getCurrentTimeMillis() + jwtExpirationMs)).signWith(getKey())
				.compact();
	}

	/**
	 * Get list of roles from authentication
	 * 
	 * @param authentication
	 * @return The JWT
	 */
	public List<String> getAuthorities(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		return userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
	}

	/**
	 * Get list of roles from roles entity
	 * 
	 * @param authentication
	 * @return The JWT
	 */
	public List<String> getAuthorities(Set<RolesEntity> rolesEntity) {
		return rolesEntity.stream().map(role -> role.getRole().name().toString()).toList();
	}
}
