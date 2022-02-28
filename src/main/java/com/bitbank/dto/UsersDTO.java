package com.bitbank.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UsersDTO {

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be present")
	private String username;

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be present")
	private String password;

}
