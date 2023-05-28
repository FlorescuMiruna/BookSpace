package com.example.bookspaceapp.model;

import com.example.bookspaceapp.configuration.JwtTokenProvider;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserWithToken {

//    private User user;
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    private JwtAuthenticationResponse jwtAuthenticationResponse;
}
