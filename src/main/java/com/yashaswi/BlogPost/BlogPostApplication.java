package com.yashaswi.BlogPost;

import com.yashaswi.BlogPost.model.Role;
import com.yashaswi.BlogPost.model.User;
import com.yashaswi.BlogPost.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogPostApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogPostApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner createdAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "master";

            if (userRepository.findByUserName(adminUsername).isEmpty()) {
                User admin = User.builder()
                        .name("Master Controller")
                        .userName(adminUsername)
                        .password(passwordEncoder.encode("adminpassword"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("Admin user created successfully!!!");
            }
        };
    }

}
