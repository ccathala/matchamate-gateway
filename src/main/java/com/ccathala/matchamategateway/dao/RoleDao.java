package com.ccathala.matchamategateway.dao;

import java.util.Optional;

import com.ccathala.matchamategateway.model.ERole;
import com.ccathala.matchamategateway.model.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleDao extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}