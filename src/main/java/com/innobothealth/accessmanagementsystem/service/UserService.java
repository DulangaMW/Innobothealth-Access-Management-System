package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    ResponseEntity<User> registerUser(UserDTO user);
    UserDetailsService userDetailsService();

}
