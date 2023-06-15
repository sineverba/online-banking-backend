package com.bitbank.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MfaDTO {

	public MfaDTO(String id, String code) {
		this.id = id;
		this.code = code;
	}

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private String id;

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private String code;
}
