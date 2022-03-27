package com.bitbank.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	 * Returns the username of authenticated user
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@GetMapping
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<MeResponse> index(HttpServletRequest httpServletRequest) {
		// Get the user from the security context
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Get the username
		String username = userDetails.getUsername();
		// Get the roles
		List<String> roles = jwtUtils.getAuthorities(SecurityContextHolder.getContext().getAuthentication());
		return ResponseEntity.status(HttpStatus.OK).body(new MeResponse(username, roles));
	}
}
