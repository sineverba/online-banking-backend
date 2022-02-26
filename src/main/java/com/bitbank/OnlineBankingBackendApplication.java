package com.bitbank;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bitbank.services.PingService;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@SpringBootApplication
public class OnlineBankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBankingBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public OpenAPI customOpenAPI() {

		/**
		 * Cannot autowire it 'cause we need it for every test. Alternative: mock it for
		 * every test.
		 */
		PingService pingService = new PingService();

		return new OpenAPI()
				.addServersItem(new Server().url("http://localhost:8080").description("Local"))
				.addServersItem(new Server().url("https://online-banking-backend.k2p.it").description("Production"))
				.components(new Components().addSecuritySchemes("bearer-key",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.info(new Info().title("Online Banking Backend").version(pingService.show())
						.description("API demo for a Online Banking Backend").license(new License().name("MIT")
								.url("https://github.com/sineverba/online-banking-backend/blob/master/LICENSE")));
	}

}
