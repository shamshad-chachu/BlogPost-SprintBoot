package com.Coorg.BlogPost.service;

import com.Coorg.BlogPost.model.User;
import com.Coorg.BlogPost.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email is already taken.");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); 
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials.");
        }
        return user;
    }
    
    public Optional<User> getUser(String email) {
    	
    	
    	return userRepository.findByEmail(email);
    }
}
