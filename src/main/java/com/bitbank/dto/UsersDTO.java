package com.bitbank.dto;

/**
 * DTO class to perform conversion to entity
 */

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bitbank.entities.RolesEntity;

import lombok.Data;

@Data
public class UsersDTO {

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private String username;

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private String password;
	
	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private Set<RolesEntity> rolesEntity;
	
}
