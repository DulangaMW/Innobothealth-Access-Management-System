package com.innobothealth.accessmanagementsystem.config;

import com.innobothealth.accessmanagementsystem.filter.JWTAuthenticationFilter;
import com.innobothealth.accessmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable);

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.GET,"/test/say-hello").permitAll()
                                .requestMatchers(HttpMethod.POST,"/user/register").permitAll()
                                .requestMatchers(HttpMethod.GET,"/test/send-email").permitAll()
                                .requestMatchers(HttpMethod.GET,"/test/send-sms").permitAll()
                                .requestMatchers(HttpMethod.POST,"/admin/register").permitAll()
                                .requestMatchers(HttpMethod.POST,"/admin/request/token").permitAll()
                                .requestMatchers(HttpMethod.POST,"/medicine/saveMedi").permitAll()
                                .requestMatchers(HttpMethod.GET,"/medicine/all").permitAll()
                                .requestMatchers(HttpMethod.GET,"/medicine/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET,"/medicine/name/{medicineName}").permitAll()
                                .requestMatchers(HttpMethod.GET,"/medicine/ex").permitAll()
                                .requestMatchers(HttpMethod.GET,"/medicine/generate-pdf/all").permitAll()
                                .requestMatchers(HttpMethod.GET,"/medicine/generate-pdf/expired").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/medicine").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/medicine/{medicineName}").permitAll()
                                .requestMatchers(HttpMethod.POST,"/claim/create").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/claim/delete").permitAll()
                                .requestMatchers(HttpMethod.GET,"/claim/getAll").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/claim/update/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/claim/approve/{email}").permitAll()


                                .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }


}
