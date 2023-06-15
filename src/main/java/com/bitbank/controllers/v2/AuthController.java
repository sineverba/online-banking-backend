package com.bitbank.controllers.v2;

import java.util.Optional;

import org.jboss.aerogear.security.otp.Totp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.dto.MfaDTO;
import com.bitbank.dto.UsersPostDTO;
import com.bitbank.entities.UsersEntity;
import com.bitbank.exceptions.InvalidMfaException;
import com.bitbank.responses.MfaResponse;
import com.bitbank.services.UserDetailsImpl;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.services.UsersService;
import com.bitbank.utils.JwtUtils;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController("AuthControllerV2")
@RequestMapping("/api/v2/auth")
@Tag(name = "Authorization - V2", description = "List of authorizations url")
public class AuthController {

	/**
	 * Create our logger
	 */
	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsServiceImpl userDetailServiceImpl;

	@Autowired
	UsersService usersService;

	@Autowired
	JwtUtils jwtUtils;

	/**
	 * Perform login. Returns a response to use in MFA, with ID
	 * 
	 * @param usersDTO
	 * @return the id of logged user.
	 */
	@PostMapping("/login")
	public ResponseEntity<MfaResponse> login(@Valid @RequestBody UsersPostDTO usersDTO) {

		String username = usersDTO.getUsername();
		String password = usersDTO.getPassword();

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Long id = userPrincipal.getId();

		return ResponseEntity.ok(new MfaResponse(Long.toString(id)));
	}

	/**
	 * Perform login. Returns a ResponseEntity with token and expiry_at
	 * 
	 * @param usersDTO
	 * @return the jwt token
	 * @throws InvalidMfaException
	 */
	@PostMapping("/verify-mfa")
	public void verifyMfa(@Valid @RequestBody MfaDTO mfaDTO) throws InvalidMfaException {

		String id = mfaDTO.getId();
		String code = mfaDTO.getCode();

		// Get the user
		Optional<UsersEntity> user = usersService.show(Long.parseLong(id));
		String secretMfa = user.get().getSecretMfa();

		Totp totp = new Totp(secretMfa);
		Boolean verified = totp.verify(code);

		throw new InvalidMfaException("The sent code is invalid.");

	}
}
