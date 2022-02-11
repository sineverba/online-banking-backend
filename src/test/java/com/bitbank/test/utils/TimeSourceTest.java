package com.bitbank.test.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.utils.TimeSource;

@ExtendWith(SpringExtension.class)
class TimeSourceTest {
	
	@Test
	void testCanGetCurrentDate() {
		TimeSource timeSource = new TimeSource();
		var currentMillis = timeSource.getCurrentTimeMillis();
		assertTrue(currentMillis >= System.currentTimeMillis());
	}

}
