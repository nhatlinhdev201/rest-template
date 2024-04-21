package com.example.loginservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v2")
public class LoginController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // kiểm tra người dùng từ bước trước
        ResponseEntity<User> checkUserResponse = restTemplate.getForEntity("http://localhost:8081/api/v1/users/check?username={username}&password={password}", User.class, user.getUserName(), user.getPassword());
        if (checkUserResponse.getStatusCode() == HttpStatus.OK && checkUserResponse.getBody() != null) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
