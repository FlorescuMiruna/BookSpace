package com.example.bookspaceapp.controller;

import com.example.bookspaceapp.exception.AlreadyExistingException;
import com.example.bookspaceapp.model.User;
import com.example.bookspaceapp.service.security.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLogInForm(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(String.valueOf(auth));
        if (auth.isAuthenticated())
            return "home";
        return "login";
    }

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

//    @PostMapping("/perform_login")
//    public String performLogin(Authentication authentication) {
//        return "redirect:/home";
//    }


}
