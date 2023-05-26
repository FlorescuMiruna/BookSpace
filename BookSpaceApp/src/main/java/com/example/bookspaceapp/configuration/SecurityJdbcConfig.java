package com.example.bookspaceapp.configuration;

import com.example.bookspaceapp.service.security.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityJdbcConfig extends WebSecurityConfigurerAdapter {

    private final JpaUserDetailsService userDetailsService;

    public SecurityJdbcConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests(auth -> auth
//                .mvcMatchers("/login", "/", "/register").permitAll()
//                .mvcMatchers("/book/*", "/book", "/home", "/logout").hasAnyRole("ADMIN", "USER"))
        // Allow all user roles to access any request
        http.authorizeRequests().anyRequest().permitAll()
                .and()
                .userDetailsService(userDetailsService)
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
                .httpBasic(withDefaults());
//                .build();
        // Disable CSRF protection
        http.csrf().disable();
    }
}
