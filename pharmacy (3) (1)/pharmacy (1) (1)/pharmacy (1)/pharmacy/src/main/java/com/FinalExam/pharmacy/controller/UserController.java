package com.FinalExam.pharmacy.controller;

import com.FinalExam.pharmacy.PharmacyApplication;
import com.FinalExam.pharmacy.model.Users;
import com.FinalExam.pharmacy.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PharmacyApplication pharmacyApplication;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        try {
            List<Users> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching all users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {

        logger.info("User registration request received for username: {}", user.getUsername());
        Users newUser = userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());

        if (newUser != null) {
            logger.info("User registered successfully with username: {}", newUser.getUsername());
            // Send email to the registered user
            pharmacyApplication.sendMail(newUser.getEmail());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } else {
            logger.error("User registration failed for username: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Users user, HttpSession session, HttpServletResponse response) {
        String email = user.getEmail();
        String password = user.getPassword();

        Users loggedInUser = userService.loginUser(email, password);

        if (loggedInUser != null) {

            session.setAttribute("loggedInUserId", loggedInUser.getId());
            session.setAttribute("userRole", loggedInUser.getRole().toString());
            session.setAttribute("username", loggedInUser.getUsername());

            Map<String, String> responseData = new HashMap<>();
            responseData.put("sessionId", loggedInUser.getId().toString());
            responseData.put("userRole", loggedInUser.getRole().toString());
            responseData.put("username", loggedInUser.getUsername());

            logger.info("User logged in successfully with Id: {}", loggedInUser.getId());
            return ResponseEntity.ok(responseData);

        } else {
            logger.warn("Invalid login attempt for email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Users user,HttpSession session, HttpServletResponse response) {
        // Logout logic
        Long userId = user.getId();
        String userRole = user.getRole().toString();
        if (userId != null && userRole != null) {
            Users users = userService.getUserById(userId);
            if (users != null) {
                logger.info("User logged out: {}", userId);

            }
        }
        if (session != null) {
            session.invalidate();
            logger.info("User logged out successfully");
            return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
        } else {
            logger.warn("No active session found for logout");
            return new ResponseEntity<>("No user logged in", HttpStatus.UNAUTHORIZED);
        }
    }
}
