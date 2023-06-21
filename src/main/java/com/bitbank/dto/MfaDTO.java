package com.bitbank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MfaDTO {

	public MfaDTO(String tempSecret, String code) {
		this.tempSecret = tempSecret;
		this.code = code;
	}

	@JsonProperty("castle_id")
	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private String tempSecret;

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be not empty")
	private String code;
}
