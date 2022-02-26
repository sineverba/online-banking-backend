package com.bitbank.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TimeSourceTest {

	@Test
	void testCanGetCurrentDate() {
		TimeSource timeSource = new TimeSource();
		var currentMillis = timeSource.getCurrentTimeMillis();
		// 1262344822000 == 01 Jan 2010 11:20:22 UTC
		assertTrue(currentMillis > 1262344822000L);
	}

}
