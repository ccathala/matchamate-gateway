package com.ccathala.matchamategateway.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Document(collection = "users")
public class User {

    @Id
    private @Getter @Setter String id;

    @NotBlank
    @Size(max = 50)
    @Email
    private @Getter @Setter String email;

    @NotBlank
    @Size(max = 120)
    private @Getter @Setter String password;

    @DBRef
    private @Getter @Setter Set<Role> roles = new HashSet<>();

    public User() {
        // No arg constructor
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
      }


}