package com.Coorg.BlogPost.controller;


import com.Coorg.BlogPost.dto.LoginDto;
import com.Coorg.BlogPost.dto.RegisterDto;
import com.Coorg.BlogPost.model.User;
import com.Coorg.BlogPost.service.AuthService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        try {
            authService.register(registerDto.getName(), registerDto.getEmail(), registerDto.getPassword());
            return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto, HttpSession session) {
        try {
            User user = authService.login(loginDto.getEmail(), loginDto.getPassword());
            // Store user ID in session to maintain the logged-in state
            session.setAttribute("userId", user.getId()); 
            // Return a success message. The session cookie will be sent automatically.
            return ResponseEntity.ok(Collections.singletonMap("message", "Login successful")).ok(loginDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate(); // Invalidate the session, logging the user out
        return ResponseEntity.ok(Collections.singletonMap("message", "Logged out successfully"));
    }
    
    @GetMapping("/user/{token}")
    public Optional<User> getUser(@PathVariable String token) {
    	Optional<User> author =authService.getUser(token);
//    	System.out.println(author);
    	return author; 
    }
}
