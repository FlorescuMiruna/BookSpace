package com.example.bookspaceapp.service.security;

import com.example.bookspaceapp.exception.AlreadyExistingException;
import com.example.bookspaceapp.model.Authority;
import com.example.bookspaceapp.model.User;
import com.example.bookspaceapp.repository.security.AuthorityRepository;
import com.example.bookspaceapp.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService{

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    private PasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User save(User user) {
        String username = user.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            throw new AlreadyExistingException("User with username: " +  username + "not found");
        }
        else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            user.setFirstName(user.getFirstName());
//            user.setLastName(user.getLastName());
            Set<Authority> authoritySet = new HashSet<>();
            Authority userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
            authoritySet.add(userRole);
            user.setAuthorities(authoritySet);
            User savedUser = userRepository.save(user);
            return savedUser;
        }

    }
}
