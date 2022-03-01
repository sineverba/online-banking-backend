package com.bitbank.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class TimeSourceTest {

	@Autowired
	private TimeSource timeSource;

	@Test
	void testCanGetCurrentDate() {
		var currentMillis = timeSource.getCurrentTimeMillis();
		// 1262344822000 == 01 Jan 2010 11:20:22 UTC
		assertTrue(currentMillis > 1262344822000L);
	}

	@Test
	void testCanGetRefreshTokenExpiryDate() {
		Instant expiryDate = timeSource.getRefreshTokenExpiryDate();
		assertTrue(expiryDate.isAfter(Instant.now()));
	}

}
