package com.bitbank.dto;

/**
 * Class to validate Users data passed
 * from Controller.
 * 
 * Valid only when submit, not other.
 * 
 */

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UsersPostDTO {

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private String username;

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private String password;

}
