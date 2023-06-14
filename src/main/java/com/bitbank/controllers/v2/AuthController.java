package com.bitbank.controllers.v2;

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

import com.bitbank.dto.UsersPostDTO;
import com.bitbank.responses.JwtResponse;
import com.bitbank.responses.MfaResponse;
import com.bitbank.services.UserDetailsImpl;
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
}
