package com.khoab.tmademo.controller;

import com.khoab.tmademo.entity.LoginRequest;
import com.khoab.tmademo.entity.UserEntity;
import com.khoab.tmademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {

    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws Exception {
        String username = loginRequest.getUsername();
        String pass = loginRequest.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pass));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Invalid credential", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Account granted", HttpStatus.OK);
    }

    //Secured API, access token is required.
    @GetMapping("/secured/findall")
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

}
