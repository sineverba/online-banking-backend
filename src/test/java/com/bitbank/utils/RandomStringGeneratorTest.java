package com.bitbank.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RandomStringGeneratorTest {
	@Test
	void testCanGenerateARandomStringOf24Chars() {
		RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
		String generatedRandomString = randomStringGenerator.getRandomString();
		assertThat(generatedRandomString).hasSize(32);
	}
}
