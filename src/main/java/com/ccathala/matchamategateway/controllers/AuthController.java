package com.ccathala.matchamategateway.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.ccathala.matchamategateway.dao.RoleDao;
import com.ccathala.matchamategateway.dao.UserDao;
import com.ccathala.matchamategateway.model.ERole;
import com.ccathala.matchamategateway.model.Role;
import com.ccathala.matchamategateway.model.User;
import com.ccathala.matchamategateway.payload.request.LoginRequest;
import com.ccathala.matchamategateway.payload.request.SignupRequest;
import com.ccathala.matchamategateway.payload.response.JwtResponse;
import com.ccathala.matchamategateway.payload.response.MessageResponse;
import com.ccathala.matchamategateway.security.jwt.JwtUtils;
import com.ccathala.matchamategateway.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
     
        UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        
        if (userDao.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email already taken"));
        }
        
        // Create user's account

    User user = new User(signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));

    Set<String> strRoles = signupRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    String roleNotFoundErrorMsg = "Error: Role is not found.";

    if (strRoles == null) {
        Role userRole = roleDao.findByName(ERole.ROLE_PLAYER)
            .orElseThrow(() -> new RuntimeException(roleNotFoundErrorMsg));
        roles.add(userRole);
    } else {
        strRoles.forEach(role -> {
            switch (role) {
                case "company":
                    Role companyRole = roleDao.findByName(ERole.ROLE_COMPANY)
                        .orElseThrow(() -> new RuntimeException(roleNotFoundErrorMsg));
                    roles.add(companyRole);
                    break;

                case "admin":
                    Role adminRole = roleDao.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(roleNotFoundErrorMsg));
                    roles.add(adminRole);
                    break;
            
                default:
                    Role userRole = roleDao.findByName(ERole.ROLE_PLAYER)
                        .orElseThrow(() -> new RuntimeException(roleNotFoundErrorMsg));
                    roles.add(userRole);
                    break;
            }
        });
    }
    
    user.setRoles(roles);
    userDao.save(user);
    
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        
    }
}