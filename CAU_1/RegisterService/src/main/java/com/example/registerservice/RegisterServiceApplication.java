package com.example.registerservice;

import com.example.registerservice.models.User;
import com.example.registerservice.repositories.UserRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RegisterServiceApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(RegisterServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Faker faker = new Faker();
                User nhatlinh = new User(1, "nhatlinh", "linh", "nhatlinhdev2010@gmail.com");
                userRepository.save(nhatlinh);
            }
        };
    }
}
