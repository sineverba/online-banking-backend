package com.bitbank.test.controllers.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.controllers.v1.PingController;
import com.bitbank.services.v1.PingService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PingController.class)
@TestPropertySource("classpath:application.properties")
class PingControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
    private PingService pingService;
	
	@Value("${app.version}")
	private String appVersion;

	@Test
	void indexShouldReturnApiVersion() throws Exception {
		
		when(pingService.show()).thenReturn(appVersion);
		
		mvc.perform(get("/api/v1/ping"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.version", is(appVersion)));
	}

}