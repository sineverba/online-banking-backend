package com.bitbank.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.responses.MeResponse;
import com.bitbank.utils.JwtUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/me")
@Tag(name = "Me", description = "Returns the username that belongs to the token")
public class MeController {
	
	@Autowired
	JwtUtils jwtUtils;
	
	/**
	 * Returns the username that belongs to the token
	 * @param httpServletRequest
	 * @return
	 */
	@GetMapping
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<MeResponse> index(HttpServletRequest httpServletRequest) {
		// Get the token from the header
		String token = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		// Fetch the username from the token
		String username = jwtUtils.getUserNameFromJwtToken(token);
		return ResponseEntity.status(HttpStatus.OK).body(new MeResponse(username));
	}
}
