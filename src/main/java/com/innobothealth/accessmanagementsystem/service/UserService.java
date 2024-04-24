package com.innobothealth.accessmanagementsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.dto.GetUserDTO;
import com.innobothealth.accessmanagementsystem.dto.TokenResponse;
import com.innobothealth.accessmanagementsystem.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    ResponseEntity<User> registerUser(UserDTO user);
    ResponseEntity<User> registerAdmin(UserDTO admin);
    UserDetailsService userDetailsService();
    TokenResponse generateToken(String email, String otp);
    List<GetUserDTO> getUsers(String userType);

}
