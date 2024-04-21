package com.example.loginservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegisterService {
    @Autowired
    private RestTemplate restTemplate;

}
