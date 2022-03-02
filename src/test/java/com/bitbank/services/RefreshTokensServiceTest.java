package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.webjars.NotFoundException;

import com.bitbank.entities.RefreshTokensEntity;
import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.RefreshTokensRepository;
import com.bitbank.repositories.UsersRepository;
import com.bitbank.utils.AuthenticationUtils;
import com.bitbank.utils.JwtUtils;
import com.bitbank.utils.TimeSource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class RefreshTokensServiceTest {
	@Mock
	private RefreshTokensRepository refreshTokensRepository;

	@Mock
	private UsersRepository usersRepository;

	@Mock
	private TimeSource timeSource;

	@Mock
	private JwtUtils jwtUtils;
	
	@Mock
	private Authentication authentication;
	
	@Mock
	private AuthenticationUtils authenticationUtils;

	@InjectMocks
	private RefreshTokensService refreshTokensService;

	/**
	 * Before each solves "This application properties is null". The value is the
	 * variable number, NOT the value on application properties
	 */
	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(timeSource, "refreshTokenDurationMs", 86400L);
	}

	/**
	 * Test can find by token
	 */

	@Test
	void testFindByToken() {
		var refreshTokensEntity = validRefreshTokensEntity(1L, "aaa", null, null);
		String token = "a1.b2.c3";
		when(refreshTokensRepository.findByToken(token)).thenReturn(Optional.of(refreshTokensEntity));
		assertEquals(Optional.of(refreshTokensEntity), refreshTokensService.findByToken(token));
	}

	/**
	 * Test can create a refresh token
	 */
	@Test
	void testCanCreateRefreshToken() {

		String refreshToken = "this-is-a-refreh-token";
		when(jwtUtils.generateRefreshToken()).thenReturn(refreshToken);

		Instant expiryDate = Instant.now();
		when(timeSource.getRefreshTokenExpiryDate()).thenReturn(expiryDate);

		// We need an usersEntity because both tables are joined.
		var usersEntity = UsersEntity.builder().id(7L).username("username").password("thisIsAPassword").build();

		var refreshTokensEntity = validRefreshTokensEntity(null, refreshToken, usersEntity, expiryDate);
		var savedRefreshTokensEntity = validRefreshTokensEntity(1L, refreshToken, usersEntity, expiryDate);
		
		when(usersRepository.findById(7L)).thenReturn(Optional.of(usersEntity));
		when(refreshTokensRepository.save(refreshTokensEntity)).thenReturn(savedRefreshTokensEntity);
		
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);
		when(authenticationUtils.getPrincipal(authentication)).thenReturn(user);

		assertEquals(savedRefreshTokensEntity, refreshTokensService.createRefreshToken(authentication));

	}

	/**
	 * Test can throw exception
	 * 
	 */
	@Test
	void testCanThrowException() {

		when(usersRepository.findById(7L)).thenReturn(Optional.empty());
		
		var usersEntity = UsersEntity.builder().id(7L).username("username").password("thisIsAPassword").build();
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);
		when(authenticationUtils.getPrincipal(authentication)).thenReturn(user);

		NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
			refreshTokensService.createRefreshToken(authentication);
		});

		String expectedMessage = "User not found";
		String actualMessage = notFoundException.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	private static RefreshTokensEntity validRefreshTokensEntity(Long id, String token, UsersEntity user,
			Instant expiryDate) {
		return RefreshTokensEntity.builder().id(id).token(token).usersEntity(user).expiryDate(expiryDate).build();
	}

}
