package com.ccathala.matchamategateway.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "roles")
public class Role {
    
    @Id
    private @Getter @Setter String id;
    private @Getter @Setter ERole name;

    public Role(){
       // No arg constructor 
    }

    public Role(ERole name){
        this.name = name;
    }

}