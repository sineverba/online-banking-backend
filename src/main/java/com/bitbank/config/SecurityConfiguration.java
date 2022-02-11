package com.bitbank.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private List<String> getAllowedOrigins() {
		return Arrays.asList("http://localhost:[*]", "https://online-banking-frontend.netlify.app",
				"https://online-banking-frontend.vercel.app");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();
		http.authorizeRequests().mvcMatchers("/api/v1/ping").permitAll()
				.mvcMatchers("/api/v1/bank-account-transactions").permitAll().anyRequest().authenticated();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(getAllowedOrigins());
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
