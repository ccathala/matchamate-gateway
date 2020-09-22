package com.ccathala.matchamategateway.dao;

import java.util.Optional;

import com.ccathala.matchamategateway.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface UserDao extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    User deleteByEmail(String email); 
}