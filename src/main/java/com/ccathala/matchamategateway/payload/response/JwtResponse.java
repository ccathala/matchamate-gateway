package com.ccathala.matchamategateway.payload.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {

    private @Getter @Setter String token;
	private @Getter @Setter String type = "Bearer";
	private @Getter @Setter String id;
	private @Getter @Setter String email;
	private @Getter List<String> roles;

    public JwtResponse(String token, String id, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String token, String email) {
        this.token = token;
        this.email = email;
    }

    
	

	


    
}