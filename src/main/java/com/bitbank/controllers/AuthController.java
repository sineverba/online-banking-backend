package com.bitbank.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.bitbank.constants.ERole;
import com.bitbank.dto.UsersDTO;
import com.bitbank.dto.UsersPostDTO;
import com.bitbank.entities.RolesEntity;
import com.bitbank.entities.UsersEntity;
import com.bitbank.exceptions.RoleOrAuthorityNotFoundException;
import com.bitbank.responses.JwtResponse;
import com.bitbank.responses.MessageResponse;
import com.bitbank.services.RolesService;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;
import com.bitbank.utils.RandomStringGenerator;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization", description = "List of authorizations url")
public class AuthController {

	/**
	 * Create our logger
	 */
	Logger logger = LoggerFactory.getLogger(AuthController.class);

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

	@Autowired
	private RolesService rolesService;

	@Value("${app.enableSubscription}")
	private Boolean enableSubscription;

	private Boolean getEnableSubscription() {
		return this.enableSubscription;
	}

	/**
	 * Performs subscription, if they are enabled.
	 * 
	 * @param usersDTO
	 * @return
	 * @throws RoleOrAuthorityNotFoundException
	 */
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<MessageResponse> post(@Valid @RequestBody UsersPostDTO usersDTO) throws RoleOrAuthorityNotFoundException {

		String username = usersDTO.getUsername();
		if (Boolean.TRUE.equals(getEnableSubscription())) {

			// Get username and password (enconded)
			String password = usersDTO.getPassword();
			String encodedPassword = passwordEncoder.encode(password);

			// Prepare for roles
			Set<RolesEntity> rolesEntity = new HashSet<>();
			RolesEntity customerRole;
			customerRole = rolesService.show(ERole.ROLE_CUSTOMER);
			rolesEntity.add(customerRole);
			
			// Prepare for secret MFA
			RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
			String generatedRandomString = randomStringGenerator.getRandomString();

			// Instance UsersDTO and populate it
			UsersDTO encodedUsersDTO = new UsersDTO();
			encodedUsersDTO.setUsername(username);
			encodedUsersDTO.setPassword(encodedPassword);
			encodedUsersDTO.setSecretMfa(generatedRandomString);
			encodedUsersDTO.setRolesEntity(rolesEntity);

			UsersEntity usersEntity = convertToEntity(encodedUsersDTO);
			usersService.post(usersEntity);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("ok", "new user created"));
		}
		logger.warn("A user with {} username has attempted to subscribe itself", username);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse("error", "subscriptions disabled"));

	}

	/**
	 * Perform login. Returns a ResponseEntity with token and expiry_at
	 * 
	 * @param usersDTO
	 * @return the jwt token
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody UsersPostDTO usersDTO) {

		String username = usersDTO.getUsername();
		String password = usersDTO.getPassword();

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// This is the token
		String jwt = jwtUtils.generateJwtToken(authentication);
		// Get the expiry at value
		String expiryAt = jwtUtils.getExpiryDateFromJwtToken(jwt).toString();

		// Get the autorithies
		List<String> roles = jwtUtils.getAuthorities(authentication);

		return ResponseEntity.ok(new JwtResponse(jwt, expiryAt, roles));
	}

	/**
	 * Refresh a token.
	 * 
	 * Return a new jwt with new expiry date.
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@PostMapping("/refresh-token")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<JwtResponse> refreshToken(HttpServletRequest httpServletRequest) {

		String token = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		String jwt = jwtUtils.generateJwtToken(token);

		// Get the expiry at value
		String expiryAt = jwtUtils.getExpiryDateFromJwtToken(jwt).toString();

		return ResponseEntity.ok(new JwtResponse(jwt, expiryAt, null));
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
