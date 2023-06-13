package com.bitbank.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitbank.services.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Value("${app.allowedOrigins}")
	private String allowedOriginsFromApplicationProperties;

	/**
	 * Return allowedOrigins from application properties
	 */
	private String getAllowedOriginsFromApplicationProperties() {
		return this.allowedOriginsFromApplicationProperties;
	}

	/**
	 * Return the allowed origins.
	 * 
	 * @return
	 */
	private List<String> getAllowedOrigins() {
		String[] allowedOrigins = this.getAllowedOriginsFromApplicationProperties().split(",");
		return Arrays.asList(allowedOrigins);
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/ping").permitAll()
						.requestMatchers("/api/v1/auth/register").permitAll().requestMatchers("/api/v1/auth/login")
						.permitAll().requestMatchers("/api-docs/**").permitAll().requestMatchers("/swagger-ui/**")
						.permitAll().requestMatchers("/swagger-ui.html").permitAll().requestMatchers("/v3/api-docs/**")
						.permitAll().requestMatchers("/documentation").permitAll().requestMatchers("/").permitAll()
						.anyRequest().authenticated())
				.cors();
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*");
			}
		};
	}

	@Bean
	CorsFilter corsFilter() {
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
