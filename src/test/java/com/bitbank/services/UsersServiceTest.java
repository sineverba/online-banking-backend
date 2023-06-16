package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.bitbank.repositories.UsersRepository;

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
		var user = UsersServiceTest.validUsersEntity(1L, "username", "password", "a1b2c3d4");
		Optional<UsersEntity> result = Optional.of(user);
		when(usersRepository.findById(1L)).thenReturn(result);
		assertEquals(1L, usersService.show(1L).get().getId());
		assertEquals("username", usersService.show(1L).get().getUsername());
	}

	/**
	 * Useful methods
	 * 
	 * @param id
	 * @param amount
	 * @param purpose
	 * @return
	 */

	private static UsersEntity validUsersEntity(Long id, String username, String password, String secretMfa) {
		return UsersEntity.builder().id(id).username(username).password(password).secretMfa(secretMfa).build();
	}
}
