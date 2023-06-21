package com.bitbank.controllers.v2;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.dto.MfaDTO;
import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.UsersRepository;
import com.bitbank.services.MfaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("classpath:application.properties")
class AuthControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MfaService mfaService;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void canLogin() throws Exception {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("password");

		var user = UsersEntity.builder().username("username").password(encodedPassword).secretMfa("123456").build();
		usersRepository.save(user);

		var userToLogin = UsersEntity.builder().username("username").password("password").build();

		when(mfaService.generateSecret()).thenReturn("secret");

		mvc.perform(post("/api/v2/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToLogin))).andExpect(status().isOk())
				.andExpect(jsonPath("$.castle_id", is("secret")));
	}

	/**
	 * Test Bad Credentials Exception
	 * 
	 */
	@Test
	void testCanThrowBadCredentialsException() throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("password");
		// Insert an user
		var user = UsersEntity.builder().username("username").password(encodedPassword).secretMfa("ABCDE").build();
		usersRepository.save(user);
		var userToLogin = UsersEntity.builder().username("username").password("wrongPassword").build();

		when(mfaService.generateSecret()).thenReturn("secret");

		mvc.perform(post("/api/v2/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToLogin))).andExpect(status().isUnauthorized());
	}

	/**
	 * Test invalid MFA exception.
	 * 
	 */
	@Test
	void testCanThrowInvalidMfaException() throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("password");
		// Insert an user
		var user = UsersEntity.builder().username("username").password(encodedPassword).secretMfa("ABCDE").build();
		usersRepository.save(user);
		// Create the MFA
		MfaDTO mfaDto = new MfaDTO("1", "123456");
		mvc.perform(post("/api/v2/auth/verify-mfa").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(mfaDto))).andExpect(status().isUnauthorized());
	}

	/**
	 * Test missing user.
	 * 
	 */
	@Test
	void testCanThrowInvalidMfaExceptionIfUserIsMissing() throws Exception {
		// Create the MFA
		MfaDTO mfaDto = new MfaDTO("1", "123456");
		mvc.perform(post("/api/v2/auth/verify-mfa").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(mfaDto))).andExpect(status().isUnauthorized());
	}

	/**
	 * Test can login via MFA.
	 * 
	 */
	@Test
	void testCanLoginViaMfa() throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("password");
		// Insert an user
		var user = UsersEntity.builder().username("username").password(encodedPassword).secretMfa("ABCDE").build();
		usersRepository.save(user);
		// Create the MFA
		MfaDTO mfaDto = new MfaDTO("1", "123456");
		// Mock the method
		when(mfaService.verify(any(), any())).thenReturn(true);
		mvc.perform(post("/api/v2/auth/verify-mfa").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(mfaDto))).andExpect(status().isOk());
	}

}