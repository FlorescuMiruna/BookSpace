package com.example.bookspaceapp.configuration;

import com.example.bookspaceapp.bootstrap.FixedCredentialsAuthenticationProvider;
import com.example.bookspaceapp.filter.CsrfLoggerFilter;
import com.example.bookspaceapp.service.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityJdbcConfig extends WebSecurityConfigurerAdapter {

//    @Value("${security.enable-csrf}")
//    private boolean csrfEnabled;

//    private final JpaUserDetailsService userDetailsService;

    private final FixedCredentialsAuthenticationProvider authenticationProvider;


//    public SecurityJdbcConfig(JpaUserDetailsService userDetailsService, FixedCredentialsAuthenticationProvider authenticationProvider) {
//        this.userDetailsService = userDetailsService;
//        this.authenticationProvider = authenticationProvider;
//    }

    public SecurityJdbcConfig(FixedCredentialsAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

//    public SecurityJdbcConfig(boolean disableDefaults, JpaUserDetailsService userDetailsService, FixedCredentialsAuthenticationProvider authenticationProvider) {
//        super(disableDefaults);
//        this.userDetailsService = userDetailsService;
//        this.authenticationProvider = authenticationProvider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers("/register", "/login", "/home").permitAll()
                .antMatchers("/home").hasAnyRole("ADMIN", "USER")
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable()
        ;


    }
}
