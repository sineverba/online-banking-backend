package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.bitbank.entities.UsersEntity;
import com.bitbank.exceptions.BalanceNotEnoughException;
import com.bitbank.repositories.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersServiceTest {
	@Mock
	private UsersRepository usersRepository;

	@InjectMocks
	private UsersService usersService;

	/**
	 * Show - can show single user
	 */

	@Test
	void get_testCanShow() {
		var user = UsersServiceTest.validUsersEntity(1L, "username", "password", "a1b2c3d4", "alfa");
		Optional<UsersEntity> result = Optional.of(user);
		when(usersRepository.findById(1L)).thenReturn(result);
		assertEquals(1L, usersService.show(1L).get().getId());
		assertEquals("username", usersService.show(1L).get().getUsername());
	}

	/**
	 * Show - can return ID from username
	 */

	@Test
	void get_testCanReturnIdFromUsername() {
		UsersEntity user = UsersServiceTest.validUsersEntity(1L, "username", "password", "a1b2c3d4", "alfa");
		Optional<UsersEntity> optionalUser = Optional.of(user);
		when(usersRepository.findByUsername("username")).thenReturn(optionalUser);
		assertEquals(1L, usersService.getIdFromUsername("username"));
	}

	/**
	 * Post section. Test can throw Entity Not Found Exception
	 * 
	 * @throws BalanceNotEnoughException
	 */

	@Test
	void testCanThrowEntityNotFoundException() {

		// Throw the exception
		EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> {
			usersService.getIdFromUsername("username");
		});

		// Compare string
		String expectedMessage = "no user found with username username";
		String actualMessage = entityNotFoundException.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/**
	 * Can save a secret into the user
	 */

	@Test
	void get_testCanStoreSecret() {
		UsersEntity user = UsersServiceTest.validUsersEntity(1L, "username", "password", "a1b2c3d4", "tempalfa");
		Optional<UsersEntity> optionalUser = Optional.of(user);
		when(usersRepository.findById(1L)).thenReturn(optionalUser);
		when(usersRepository.save(optionalUser.get())).thenReturn(optionalUser.get());
		optionalUser.get().setTempSecret("tempSecret");
		assertTrue(usersService.setTempSecret(1L, "secret"));
	}

	/**
	 * Can return false if saved store is different
	 */

	@Test
	void get_testCanHandleCannotStoreSecret() {
		UsersEntity user = UsersServiceTest.validUsersEntity(1L, "username", "password", "a1b2c3d4", "alfa");
		Optional<UsersEntity> optionalUser = Optional.of(user);
		// A different user
		UsersEntity differentUser = UsersServiceTest.validUsersEntity(1L, "anotherone", "password", "a1b2c3d4", "beta");
		Optional<UsersEntity> optionalDifferentUser = Optional.of(differentUser);
		when(usersRepository.findById(1L)).thenReturn(optionalUser);
		when(usersRepository.save(optionalUser.get())).thenReturn(optionalDifferentUser.get());
		optionalUser.get().setTempSecret("tempSecret");
		assertFalse(usersService.setTempSecret(1L, "secret"));
	}

	/**
	 * Post section. Test can throw BalanceNotEnoughException
	 * 
	 * @throws BalanceNotEnoughException
	 */

	@Test
	void testCanThrowEntityNotFoundExceptionOnPost() {

		// Throw the exception
		EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> {
			usersService.setTempSecret(1L, "secret");
		});
		// Compare string
		String expectedMessage = "no user found with id 1";
		String actualMessage = entityNotFoundException.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/**
	 * Useful methods
	 * 
	 * @param id
	 * @param amount
	 * @param purpose
	 * @return
	 */

	private static UsersEntity validUsersEntity(Long id, String username, String password, String secretMfa,
			String tempSecret) {
		return UsersEntity.builder().id(id).username(username).password(password).secretMfa(secretMfa)
				.tempSecret(tempSecret).build();
	}
}
