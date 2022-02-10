package com.bitbank;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OnlineBankingBackendApplicationTests {

	@Test
	void contextLoad() {
		String start = "start";
		String end = "end";
		assertNotEquals(start, end);
	}

}
