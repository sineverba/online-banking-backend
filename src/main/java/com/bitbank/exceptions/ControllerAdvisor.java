package com.bitbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bitbank.responses.ErrorResponse;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	/**
	 * Manage the Balance Not Enough Exception.
	 * 
	 * Return the entity with Error response.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BalanceNotEnoughException.class)
	protected ResponseEntity<ErrorResponse> handleBalanceNotEnough(BalanceNotEnoughException ex) {
		return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
	}

	/**
	 * Manage AccessDeniedException
	 * 
	 * E.g. access to routes with different authority
	 * 
	 */
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ex.getLocalizedMessage()));
	}

	/**
	 * Manage RoleOrAuthorityNotFoundException.
	 * 
	 * Return the entity with Error response.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RoleOrAuthorityNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleRoleOrAuthorityNotFound(RoleOrAuthorityNotFoundException ex) {
		return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
	}

	/**
	 * Handle InvalidMfaException.
	 * 
	 * Return the entity with Error response.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidMfaException.class)
	protected ResponseEntity<ErrorResponse> handleInvalidMfaException(InvalidMfaException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ex.getMessage()));
	}
	
	/**
	 * Handle BadCredentialsException
	 * 
	 * Return the entity with Error response.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ex.getMessage()));
	}

}
