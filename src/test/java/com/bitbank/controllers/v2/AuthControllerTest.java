package com.bitbank.controllers.v2;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("classpath:application.properties")
class AuthControllerTest {

	@Autowired
	private MockMvc mvc;

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

		mvc.perform(post("/api/v2/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToLogin))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("1")));
	}

}