package com.example.bookspaceapp.bootstrap;

import com.example.bookspaceapp.model.Authority;
import com.example.bookspaceapp.model.User;
import com.example.bookspaceapp.repository.security.AuthorityRepository;
import com.example.bookspaceapp.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private void loadUserData() {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("12345"))
                    .email("admin@admin.com")
                    .firstName("admin")
                    .lastName("admin")
                    .authority(adminRole)
                    .build();

            User user = User.builder()
                    .username("reader")
                    .password(passwordEncoder.encode("12345"))
                    .email("user@user.com")
                    .firstName("user")
                    .lastName("user")
                    .authority(userRole)
                    .build();

            userRepository.save(admin);
            userRepository.save(user);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}