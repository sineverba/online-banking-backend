package com.bitbank.controllers.v1;

import java.util.LinkedHashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.bitbank.dto.v1.UsersDTO;
import com.bitbank.entities.v1.UsersEntity;
import com.bitbank.services.v1.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;

@RestController
@RequestMapping("/api/v1/auth")
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

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, String> post(@RequestBody UsersDTO usersDTO) {

		String username = usersDTO.getUsername();
		String password = usersDTO.getPassword();
		String encodedPassword = passwordEncoder.encode(password);

		UsersDTO encodedUsersDTO = new UsersDTO();
		encodedUsersDTO.setUsername(username);
		encodedUsersDTO.setPassword(encodedPassword);

		UsersEntity usersEntity = convertToEntity(encodedUsersDTO);
		usersService.post(usersEntity);
		Map<String, String> map = new LinkedHashMap<>();
		map.put("status", "ok - new user created");
		return map;
	}

	@PostMapping("/login")
	public Map<String, String> login(@RequestBody UsersDTO usersDTO) {

		String username = usersDTO.getUsername();
		String password = usersDTO.getPassword();

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		Map<String, String> map = new LinkedHashMap<>();
		map.put("access_token", jwt);
		return map;
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
