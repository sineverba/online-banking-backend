package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.constants.ERole;
import com.bitbank.entities.RolesEntity;
import com.bitbank.entities.UsersEntity;
import com.bitbank.services.UserDetailsImpl;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;
import com.bitbank.utils.TimeSource;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("classpath:application.properties")
class MeControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	AuthTokenFilter authTokenFilter;

	@MockBean
	AuthEntryPointJwt authEntryPointJwt;

	@MockBean
	JwtUtils jwtUtils;

	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	Authentication authentication;

	@MockBean
	SecurityContext securityContext;

	@MockBean
	private TimeSource timeSource;

	@WithMockUser(username = "testusername", authorities = { "ROLE_CUSTOMER" })
	@Test
	void testCanReturnUsername() throws Exception {

		// Add the Security Context
		SecurityContextHolder.setContext(securityContext);

		// Create an usersEntity to build by userDetailsImpl
		// ADMIN - Initialize the set
		Set<RolesEntity> adminRole = new HashSet<>();
		// ADMIN - Generate the entity
		RolesEntity adminRolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));
		// ADMIN - Add the entity to the set
		adminRole.add(adminRolesEntity);
		UsersEntity usersEntity = new UsersEntity(1L, "testusername", "password", adminRole);
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		// Mock some method...
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);

		// Create a fake token
		String token = "a1.b2.c3";

		// Mock return
		when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn("testusername");

		mvc.perform(get("/api/v1/me").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.username", is("testusername")));
	}

	@WithMockUser(username = "testusername", authorities = { "ROLE_CUSTOMER" })
	@Test
	void testCanReturnsRolesList() throws Exception {

		// Add the Security Context
		SecurityContextHolder.setContext(securityContext);

		// Create an usersEntity to build by userDetailsImpl
		// ADMIN - Initialize the set
		Set<RolesEntity> adminRole = new HashSet<>();
		// ADMIN - Generate the entity
		RolesEntity adminRolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));
		// ADMIN - Add the entity to the set
		adminRole.add(adminRolesEntity);
		UsersEntity usersEntity = new UsersEntity(1L, "testusername", "password", adminRole);
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		// Mock some method...
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);

		// Create a fake token
		String token = "a1.b2.c3";

		// Mock return
		when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn("testusername");

		// Create roles for the return
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_CUSTOMER");
		when(jwtUtils.getAuthorities(any(Authentication.class))).thenReturn(roles);

		mvc.perform(get("/api/v1/me").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.username", is("testusername")))
				.andExpect(jsonPath("$.roles").isNotEmpty()).andExpect(jsonPath("$.roles").isArray());
	}

	/**
	 * Generate a RolesEntity
	 */
	private static RolesEntity validRolesEntity(Long id, ERole role) {
		return RolesEntity.builder().id(id).role(role).build();
	}

}