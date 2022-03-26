package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.constants.ERole;
import com.bitbank.entities.RolesEntity;
import com.bitbank.entities.UsersEntity;
import com.bitbank.exceptions.RoleOrAuthorityNotFoundException;
import com.bitbank.repositories.RolesRepository;
import com.bitbank.services.RolesService;
import com.bitbank.services.UserDetailsImpl;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@TestPropertySource("classpath:application.properties")
class AuthControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	AuthTokenFilter authTokenFilter;

	@MockBean
	AuthEntryPointJwt authEntryPointJwt;

	@MockBean
	JwtUtils jwtUtils;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	Authentication authentication;

	@MockBean
	SecurityContext securityContext;

	@MockBean
	private RolesService rolesService;

	@MockBean
	private RolesRepository rolesRepository;

	/**
	 * Generate an authority before every test
	 * 
	 * @return
	 */
	@BeforeEach
	private void buildAdminRoleAuthority() {
		// ADMIN - Initialize the set
		Set<RolesEntity> adminRole = new HashSet<>();
		// ADMIN - Generate the entity
		RolesEntity adminRolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));
		// ADMIN - Add the entity to the set
		adminRole.add(adminRolesEntity);
		this.adminRole = adminRole;
	}

	@BeforeEach
	private void buildCustomerRoleAuthority() {
		// CUSTOMER - Initialize the set
		Set<RolesEntity> customerRole = new HashSet<>();
		// CUSTOMER - Generate the entity
		RolesEntity customerRolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_CUSTOMER"));
		// CUSTOMER - Add the entity to the set
		customerRole.add(customerRolesEntity);
		this.customerRole = customerRole;
	}

	/**
	 * The autority to use
	 * 
	 * @throws Exception
	 */
	private Set<RolesEntity> adminRole;
	private Set<RolesEntity> customerRole;

	private Set<RolesEntity> getAdminRole() {
		return this.adminRole;
	}

	private Set<RolesEntity> getCustomerRole() {
		return this.customerRole;
	}

	@Test
	@WithMockUser(username = "username", authorities = { "ROLE_ADMIN" })
	void canRegisterNewUser() throws Exception {

		// Create a valid user
		var userToSave = validUserEntity("username", "password", this.getCustomerRole());
		var savedUser = validUserEntity("username", "a1.b2.c3", this.getCustomerRole());

		// Create valid role entity
		RolesEntity rolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_CUSTOMER"));
		// Create an optional result
		RolesEntity result = Optional.of(rolesEntity).get();

		// Mock some methods
		when(userDetailsServiceImpl.post(userToSave)).thenReturn(savedUser);
		when(rolesService.show(ERole.valueOf("ROLE_CUSTOMER"))).thenReturn(result);

		mvc.perform(post("/api/v1/auth/register/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToSave))).andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(username = "username", authorities = { "ROLE_ADMIN" })
	void canLogin() throws Exception {

		// Add security context
		SecurityContextHolder.setContext(securityContext);

		// Create a new usersentity to deal with authentication
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password", this.getAdminRole());
		// Build an user from usersEntity
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		// Mock some methods
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);

		String token = jwtUtils.generateJwtToken(authentication);
		Long expiryDate = jwtUtils.getExpiryDateFromJwtToken(token);

		// Create an usersEntity to pass to the login
		UsersEntity userToLogin = validUserEntity("username", "password", this.getAdminRole());

		mvc.perform(post("/api/v1/auth/login/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToLogin))).andExpect(status().isOk())
				.andExpect(jsonPath("$.access_token", is(token)))
				.andExpect(jsonPath("$.expiry_at", is(expiryDate.toString())))
				.andExpect(jsonPath("$.roles", Matchers.empty()));
	}

	/**
	 * Perform several tests, with invalid input.
	 * 
	 * @param invalidUsersEntity
	 * @throws Exception
	 */
	@ParameterizedTest
	@MethodSource("getInvalidUsers")
	void testCanCatchExceptionOnLogin(UsersEntity invalidUsersEntity) throws Exception {

		mvc.perform(post("/api/v1/auth/login/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(invalidUsersEntity))).andExpect(status().isBadRequest());

	}

	@Test
	@WithMockUser(username = "username", authorities = { "ROLE_CUSTOMER" })
	void testCanRefreshToken() throws Exception {

		// Add the Security Context
		SecurityContextHolder.setContext(securityContext);

		// Create an usersEntity to build by userDetailsImpl
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password", this.getAdminRole());
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		// Mock some method...
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);

		// Create a fake token
		String token = "a1.b2.c3";
		String refreshedToken = "re.fre.shed";
		Long expiryAt = 19999999L;
		when(jwtUtils.generateJwtToken(authentication)).thenReturn(token);
		when(jwtUtils.generateJwtToken(token)).thenReturn(refreshedToken);
		when(jwtUtils.getExpiryDateFromJwtToken(refreshedToken)).thenReturn(expiryAt);

		// Make the call
		mvc.perform(post("/api/v1/auth/refresh-token/").contentType(MediaType.APPLICATION_JSON).header("Authorization",
				token)).andExpect(status().isOk()).andExpect(jsonPath("$.access_token", is(refreshedToken)))
				.andExpect(jsonPath("$.expiry_at", is(expiryAt.toString())));
	}

	/**
	 * Perform several tests, with invalid input.
	 * 
	 * @param invalidUsersEntity
	 * @throws Exception
	 */
	@ParameterizedTest
	@MethodSource("getInvalidUsers")
	void testCanCatchExceptionOnSubscription(UsersEntity invalidUsersEntity) throws Exception {

		mvc.perform(post("/api/v1/auth/register/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(invalidUsersEntity))).andExpect(status().isBadRequest());

	}

	/**
	 * Return a series of invalid users
	 * 
	 * @return
	 */
	private static Stream<UsersEntity> getInvalidUsers() {
		// ADMIN - Initialize the set
		Set<RolesEntity> adminRole = new HashSet<>();
		// ADMIN - Generate the entity
		RolesEntity adminRolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));
		// ADMIN - Add the entity to the set
		adminRole.add(adminRolesEntity);
		// Create a valid entity
		var validUsersEntity = validUserEntity("username", "password", adminRole);

		return Stream.of(validUsersEntity.toBuilder().username(null).build(),
				validUsersEntity.toBuilder().username("").build(), validUsersEntity.toBuilder().password(null).build(),
				validUsersEntity.toBuilder().password("").build());
	}

	/**
	 * Test RoleOrAuthorityNotFoundException
	 * 
	 */
	@Test
	void canCatchRoleOrAuthorityNotFoundException() throws Exception {

		// Create a valid user
		var userToSave = validUserEntity("username", "password", this.getAdminRole());

		when(rolesService.show(any())).thenThrow(new RoleOrAuthorityNotFoundException("cannot find role ADMIN_ROLE"));

		mvc.perform(post("/api/v1/auth/register/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToSave))).andExpect(status().isBadRequest());
	}

	/**
	 * Generate a user entity
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private static UsersEntity validUserEntity(String username, String password, Set<RolesEntity> roles) {
		return UsersEntity.builder().username(username).password(password).rolesEntity(roles).build();
	}

	/**
	 * Generate a RolesEntity
	 */
	private static RolesEntity validRolesEntity(Long id, ERole role) {
		return RolesEntity.builder().id(id).role(role).build();
	}

}