package com.ccathala.matchamategateway.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class LoginRequest {
    @NotBlank
    @Email
	private @Getter @Setter String email;

	@NotBlank
	private @Getter @Setter String password;

	
}