package com.bitbank.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.bitbank.entities.RefreshTokensEntity;
import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.RefreshTokensRepository;
import com.bitbank.repositories.UsersRepository;
import com.bitbank.utils.JwtUtils;
import com.bitbank.utils.TimeSource;

@Service
public class RefreshTokensService {
	@Autowired
	private RefreshTokensRepository refreshTokensRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private TimeSource timeSource;

	@Autowired
	private JwtUtils jwtUtils;

	public Optional<RefreshTokensEntity> findByToken(String token) {
		return refreshTokensRepository.findByToken(token);
	}

	/**
	 * Create a refresh token.
	 *
	 * @param userId
	 * @return
	 */
	public RefreshTokensEntity createRefreshToken(Long userId) {

		Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(userId);

		if (optionalUsersEntity.isEmpty()) {
			throw new NotFoundException("User not found");
		}

		RefreshTokensEntity refreshTokensEntity = new RefreshTokensEntity();
		refreshTokensEntity.setUsersEntity(optionalUsersEntity.get());
		refreshTokensEntity.setExpiryDate(timeSource.getRefreshTokenExpiryDate());
		refreshTokensEntity.setToken(jwtUtils.generateRefreshToken());
		return refreshTokensRepository.save(refreshTokensEntity);
	}

}
