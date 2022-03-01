package com.bitbank.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.entities.RefreshTokensEntity;
import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.RefreshTokensRepository;
import com.bitbank.repositories.UsersRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
// DirtiesContext reset DB before each test method
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD) 
class RefreshTokensRepositoryTest {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private RefreshTokensRepository refreshTokensRepository;

	/**
	 * Index
	 * 
	 * @return
	 */
	@Test
	void testCanFindAll() {

		usersRepository.save(validUsersEntity());

		List<RefreshTokensEntity> list = new ArrayList<RefreshTokensEntity>();
		List<RefreshTokensEntity> result = new ArrayList<RefreshTokensEntity>();

		list.add(validRefreshTokensEntity());

		for (RefreshTokensEntity entity : list) {
			refreshTokensRepository.save(entity);
			result.add(entity);
		}

		assertEquals(result, refreshTokensRepository.findAll());

	}

	private static UsersEntity validUsersEntity() {
		return UsersEntity.builder().id(1L).username("username").password("password").build();
	}

	private static RefreshTokensEntity validRefreshTokensEntity() {
		return RefreshTokensEntity.builder().id(1L).token("a1.b2.c3").usersEntity(validUsersEntity())
				.expiryDate(Instant.now()).build();
	}
}
