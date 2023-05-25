package com.example.bookspaceapp.controller;

import com.example.bookspaceapp.exception.AlreadyExistingException;
import com.example.bookspaceapp.model.User;
import com.example.bookspaceapp.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
;

@RestController
//@RequestMapping("/user")
@Slf4j
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/login")
//    public String showLogInForm(){ return "login"; }
//    @PostMapping("/login")
//    public ResponseEntity<User> login(@RequestBody User user) {
//        authenticate(user.getUsername(), user.getPassword());
//        User loginUser = userService.findUserByUsername(user.getUsername());
//        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
//        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
//        return new ResponseEntity<>(loginUser, jwtHeader, OK);
//    }

//    @GetMapping("/access-denied")
//    public String accessDeniedPage(){ return "access-denied"; }
//
//    @RequestMapping({"","/","/home"})
//    public ModelAndView getHome(){
//        return new ModelAndView("home");
//    }
//
//    @GetMapping("/register")
//    public String showRegisterForm(Model model){
//        model.addAttribute("user", new User());
//        return "register";
//    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid User user){
        try {
            userService.save(user);
            return "New User registered!";
        } catch (AlreadyExistingException e){
            return "User already existing!";
        }

    }

}
