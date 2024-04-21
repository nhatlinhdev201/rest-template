package com.example.loginservice.controllers;

import com.example.registerservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v2")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/checkUser")
    public ResponseEntity<User> checkUser(@RequestParam String username, @RequestParam String password) {
        String url = "http://localhost:8081/api/v1/users/check?username={username}&password={password}";
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class, username, password);
        if (response.getBody() != null) {
            return response;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
