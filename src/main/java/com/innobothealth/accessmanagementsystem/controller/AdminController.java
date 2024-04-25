package com.innobothealth.accessmanagementsystem.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.dto.TokenResponse;
import com.innobothealth.accessmanagementsystem.dto.UserDTO;
import com.innobothealth.accessmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<User> registerAdmin(@RequestBody UserDTO user) {
        return userService.registerAdmin(user);
    }

    @PostMapping("request/token")
    public TokenResponse getAccessToken(@RequestBody JsonNode jsonNode) {
        return userService.generateToken(jsonNode.get("email").asText(), jsonNode.get("otp").asText());
    }

    @GetMapping("getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("delete/{id}")
    public void getAllUsers(@PathVariable String id) {
        userService.deleteUSer(id);
    }

}
