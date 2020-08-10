package com.ccathala.matchamategateway.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class SignupRequest {

    @NotBlank
    @Size(max = 50)
    @Email
    private @Getter @Setter String email;

    private @Getter @Setter Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private @Getter @Setter String password;

    @NotBlank
    @Size(min = 6, max = 40)
    private @Getter @Setter String passwordConfirm;

    public SignupRequest() {
    }

    public SignupRequest(String email, Set<String> roles, String password, String passwordConfirm) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

}