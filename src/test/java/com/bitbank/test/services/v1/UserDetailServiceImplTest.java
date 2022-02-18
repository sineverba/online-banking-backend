package com.bitbank.test.services.v1;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bitbank.entities.v1.UsersEntity;
import com.bitbank.repository.v1.UsersRepository;
import com.bitbank.services.v1.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

	@Mock
	private UsersRepository usersRepository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailServiceImpl;

	private static UsersEntity usersEntity(Long id, String username, String password) {
		return UsersEntity.builder().id(id).username(username).password(password).build();
	}

	@Test
	void testCanLoadUserByUsername() {

		UsersEntity usersEntity = usersEntity(1L, "username", "password");
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
		var userToSave = validUsersEntity("username", "password");
		var savedUser = validUsersEntity(1L, "username", "password");

		when(usersRepository.save(userToSave)).thenReturn(savedUser);

		assertEquals(savedUser, userDetailServiceImpl.post(userToSave));
	}

	private static UsersEntity validUsersEntity(Long id, String username, String password) {
		return UsersEntity.builder().id(id).username(username).password(password).build();
	}

	private static UsersEntity validUsersEntity(String username, String password) {
		return UsersEntity.builder().username(username).password(password).build();
	}

}
