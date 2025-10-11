package com.shubham.Echoes.controller;

import com.shubham.Echoes.entity.User;
import com.shubham.Echoes.service.UserDetailsServiceImpl;
import com.shubham.Echoes.service.UserService;
import com.shubham.Echoes.utilis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "App is running fine!";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            if (userService.findByUserName(user.getUserName()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists!");
            }
            userService.saveNewUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            // fallback for Heroku demo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user (demo mode)");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            user.getPassword()
                    )
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            // fallback for demo, allow a "demo" user if DB unavailable
            if ("demoUser".equals(user.getUserName()) && "demoPass".equals(user.getPassword())) {
                return ResponseEntity.ok("demo-token-for-heroku");
            }
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
