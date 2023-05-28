package com.example.bookspaceapp.model;

import com.example.bookspaceapp.configuration.JwtTokenProvider;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserWithToken {

    private User user;

    private JwtAuthenticationResponse jwtAuthenticationResponse;
}
