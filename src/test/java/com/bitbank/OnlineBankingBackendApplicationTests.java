package com.bitbank;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineBankingBackendApplicationTests {

	@Test
	void contextLoad() {
		String start = "start";
		String end = "end";
		assertNotEquals(start, end);
	}

}
