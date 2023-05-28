package com.example.bookspaceapp.controller;

import com.example.bookspaceapp.configuration.JwtTokenProvider;
import com.example.bookspaceapp.exception.AlreadyExistingException;
import com.example.bookspaceapp.model.JwtAuthenticationResponse;
import com.example.bookspaceapp.model.User;
import com.example.bookspaceapp.model.UserWithToken;
import com.example.bookspaceapp.service.security.JpaUserDetailsService;
import com.example.bookspaceapp.service.security.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
public class UserController {
    UserService userService;

    JpaUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JpaUserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    //    @GetMapping("/login")
//    public String showLogInForm(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        log.info(String.valueOf(auth));
//        if (auth.isAuthenticated())
//            return "home";
//        return "login";
//    }

//    @GetMapping("/access-denied")
//    public String accessDeniedPage(){ return "access-denied"; }
//
    @GetMapping({"","/","/home"})
    public String getHome(){
        return "Welcome to BookSpace App!";
    }

    @PostMapping("/register")
    public User register(@RequestBody @Valid User user){
            userService.save(user);
            return user;

    }

//    @GetMapping("/login")
//    public String showLogInForm(){ return "login"; }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        try {
//            // Authenticate the user
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//            );
//
//            // Generate JWT token
//            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
//            String token = jwtTokenProvider.generateToken(userDetails);
//
//            // Return the token in the response
//            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
//        } catch (AuthenticationException e) {
//            // Return error response if authentication fails
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

//        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())

            // Generate JWT token
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtTokenProvider.generateToken(userDetails);

            User userAuth = userService.getFullUserByUsername(user.getUsername());

            // Return the token in the response
            return ResponseEntity.ok(new UserWithToken(userAuth.getId(), userAuth.getUsername(), userAuth.getPassword(),
                    userAuth.getFirstName(), userAuth.getLastName(), userAuth.getEmail(), new JwtAuthenticationResponse(token)));
//        try {
//            // Authenticate the user
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//            );
//
//            // Generate JWT token
//            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
//            String token = jwtTokenProvider.generateToken(userDetails);
//
//            // Return the token in the response
//            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
//        } catch (AuthenticationException e) {
//            // Return error response if authentication fails
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
    }

//    @PostMapping("/perform_login")
//    public String performLogin(Authentication authentication) {
//        return "redirect:/home";
//    }


}
