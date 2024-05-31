package com.FinalExam.pharmacy.service.impl;

import com.FinalExam.pharmacy.model.UserRole;
import com.FinalExam.pharmacy.model.Users;
import com.FinalExam.pharmacy.repo.UserRepository;
import com.FinalExam.pharmacy.service.UserService;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

//    @Autowired
//    private JwtUtil jwtUtil;
//    public final String TOKEN_PREFIX = "Bearer ";
//    public final String HEADER_STRING = "Authorization";
//
//    public ResponseEntity<?> authenticate(String username, String password) throws JSONException {
//        Optional<Users> optionalUser = userRepository.findFirstByUsername(username);
//
//        if (optionalUser.isPresent()) {
//            Users user = optionalUser.get();
//            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
//                final String jwt = jwtUtil.generateToken( user.getUsername(), user.getId(), user.getRole().toString());
//                return ResponseEntity.ok()
//                        .header(HEADER_STRING, TOKEN_PREFIX + jwt)
//                        .body(new JSONObject()
//                                .put("userId", user.getId())
//                                .put("username", user.getUsername())
//                                .put("role", user.getRole().toString())
//                                .toString());
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
//        }
//    }
//
//    public Boolean hasUserWithEmail(String email){
//        return userRepository.findFirstByEmail(email).isPresent();
//
//    }
//
//    public Boolean hasUserWithUsername(String username){
//        return userRepository.findFirstByUsername(username).isPresent();
//
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Users> optionalUser = userRepository.findFirstByUsername(username);
//
//        if(optionalUser.isEmpty()) throw  new UsernameNotFoundException("Username not found", null);
//
//        return  new org.springframework.security.core
//                .userdetails
//                .User(
//                        optionalUser.get().getEmail(),
//                optionalUser.get().getPassword(),
//                new ArrayList<>()
//        );
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(Users user) {
        UserRole role = user.getRole();
        if (role == null || role == UserRole.CUSTOMER) {
            return new String[]{UserRole.CUSTOMER.toString()};
        } else {
            return new String[]{UserRole.ADMIN.toString()};
        }
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public Users registerUser(String username, String email, String password) {

        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        // here we do our encryption

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        user.setPassword(encodedPassword);
        // if   I want to change
        user.setRole(UserRole.CUSTOMER);
        return userRepository.save(user);
    }

    @Override
    public Users loginUser(String email, String password) {

        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && bCryptPasswordEncoder.matches(password, userOptional.get().getPassword())) {
            return userOptional.get(); // Login successful
        } else {
            return null; // Login failed
        }

    }

    @Override
    public Users getUserByEmail(String email) {
        Optional<Users> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
        // return userRepository.findByUsername(email);
    }

    @Override
    public Users getUserById(Long adminId) {
        Optional<Users> userOptional = userRepository.findById(adminId);
        return userOptional.orElse(null);
    }
}