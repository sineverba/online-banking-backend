package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.UsersRepository;
import com.bitbank.utils.RandomStringGenerator;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

	@MockBean
	private RandomStringGenerator randomStringGenerator;

	@Mock
	private UsersRepository usersRepository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailServiceImpl;

	@Test
	void testCanLoadUserByUsername() {

		UsersEntity usersEntity = validUsersEntity(1L, "username", "password");
		Optional<UsersEntity> result = Optional.of(usersEntity);

		when(usersRepository.findByUsername("username")).thenReturn(result);

		UserDetails userDetails = userDetailServiceImpl.loadUserByUsername("username");

		assertEquals("username", userDetails.getUsername());

	}

	@Test
	void testCanThrowException() {

		UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> {
			userDetailServiceImpl.loadUserByUsername("username");
		});

		String expectedMessage = "User not found";
		String actualMessage = usernameNotFoundException.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/**
	 * Post section
	 */

	@Test
	void post_testCanSaveItem() {

		String randomString = "a1b2c3";
		when(randomStringGenerator.getRandomString()).thenReturn(randomString);

		var userToSave = validUsersEntity("username", "password");
		var savedUser = validUsersEntity(1L, "username", "password", randomString);

		when(usersRepository.save(userToSave)).thenReturn(savedUser);

		assertEquals(savedUser, userDetailServiceImpl.post(userToSave));
	}

	/**
	 * Generate a valid user entity for tests.
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @return
	 */
	private static UsersEntity validUsersEntity(Long id, String username, String password) {
		return UsersEntity.builder().id(id).username(username).password(password).build();
	}

	private static UsersEntity validUsersEntity(String username, String password) {
		return UsersEntity.builder().username(username).password(password).build();
	}

	private static UsersEntity validUsersEntity(Long id, String username, String password, String secretMfa) {
		return UsersEntity.builder().id(id).username(username).password(password).secretMfa(secretMfa).build();
	}

}
