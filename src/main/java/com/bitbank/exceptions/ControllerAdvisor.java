package com.bitbank.exceptions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bitbank.responses.ErrorResponse;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, List<String>> result = ex.getBindingResult().getAllErrors().stream().map(FieldError.class::cast)
				.collect(Collectors.groupingBy(FieldError::getField,
						Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())));

		return ResponseEntity.badRequest().body(result);
	}

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

}
