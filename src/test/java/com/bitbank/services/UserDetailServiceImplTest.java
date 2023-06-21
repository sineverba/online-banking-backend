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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

	@MockBean
	private MfaService mfaService;

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
		when(mfaService.generateSecret()).thenReturn(randomString);

		var userToSave = validUsersEntity("username", "password");
		var savedUser = validUsersEntity(1L, "username", "password", randomString);

		when(usersRepository.save(userToSave)).thenReturn(savedUser);

		assertEquals(savedUser, userDetailServiceImpl.post(userToSave));
	}

	/**
	 * Can find by id
	 */
	@Test
	void testCanLoadById() {

		UsersEntity usersEntity = validUsersEntity(1L, "username", "password");
		Optional<UsersEntity> result = Optional.of(usersEntity);

		when(usersRepository.findById(Long.valueOf(1))).thenReturn(result);

		UserDetails userDetails = userDetailServiceImpl.loadUserById(1);

		assertEquals("username", userDetails.getUsername());

	}

	/**
	 * Can throw exception looking for ID
	 */
	@Test
	void testCanThrowExceptionFindById() {

		UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> {
			userDetailServiceImpl.loadUserById(1);
		});

		String expectedMessage = "User not found";
		String actualMessage = usernameNotFoundException.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
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
