package com.bitbank.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.dto.UsersDTO;
import com.bitbank.entities.UsersEntity;
import com.bitbank.responses.JwtResponse;
import com.bitbank.responses.MessageResponse;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization", description = "List of authorizations url")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl usersService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@Value("${app.enableRegistration}")
	private Boolean enableRegistration;

	private Boolean getEnableRegistration() {
		return this.enableRegistration;
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<MessageResponse> post(@RequestBody UsersDTO usersDTO) {

		if (Boolean.TRUE.equals(getEnableRegistration())) {
			String username = usersDTO.getUsername();
			String password = usersDTO.getPassword();
			String encodedPassword = passwordEncoder.encode(password);

			UsersDTO encodedUsersDTO = new UsersDTO();
			encodedUsersDTO.setUsername(username);
			encodedUsersDTO.setPassword(encodedPassword);

			UsersEntity usersEntity = convertToEntity(encodedUsersDTO);
			usersService.post(usersEntity);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("ok", "new user created"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse("error", "registrations disabled"));

	}

	/**
	 * Perform login. Returns a ResponseEntity with token and expiry_at
	 * 
	 * @param usersDTO
	 * @return the jwt token
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody UsersDTO usersDTO) {

		String username = usersDTO.getUsername();
		String password = usersDTO.getPassword();

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// This is the token
		String jwt = jwtUtils.generateJwtToken(authentication);
		// Get the expiry at value
		String expiryAt = jwtUtils.getExpiryDateFromJwtToken(jwt).toString();

		return ResponseEntity.ok(new JwtResponse(jwt, expiryAt));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<JwtResponse> refreshToken(HttpServletRequest httpServletRequest) {

		String token = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		String jwt = jwtUtils.generateJwtToken(token);

		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	/**
	 * Convert the DTO to the entity
	 * 
	 * @param bankAccountTransactionsEntity
	 * @return
	 */
	private UsersEntity convertToEntity(UsersDTO usersDTO) {
		return modelMapper.map(usersDTO, UsersEntity.class);
	}
}
