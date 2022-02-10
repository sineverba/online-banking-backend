package com.bitbank.test.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.bitbank.services.v1.PingService;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class PingServiceTest {
	
	@Autowired
	private PingService pingService;
	
	@Value("${app.version}")
	private String appVersion;
	
	@Test
	void canShowVersion() {
		assertEquals(appVersion, pingService.show());
	}

}
