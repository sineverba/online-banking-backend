package com.bitbank.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.entities.RefreshTokensEntity;
import com.bitbank.repositories.RefreshTokensRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RefreshTokensRepositoryTest {
	@Autowired
	private RefreshTokensRepository refreshTokensRepository;

	/**
	 * Index
	 * 
	 * @return
	 */
	@Test
	void testCanFindAll() {

		List<RefreshTokensEntity> list = new ArrayList<RefreshTokensEntity>();
		List<RefreshTokensEntity> result = new ArrayList<RefreshTokensEntity>();

		list.add(validRefreshTokensEntity());

		for (RefreshTokensEntity entity : list) {
			refreshTokensRepository.save(entity);
			result.add(entity);
		}

		assertEquals(result, refreshTokensRepository.findAll());

	}

	private static RefreshTokensEntity validRefreshTokensEntity() {
		return RefreshTokensEntity.builder().build();
	}
}
