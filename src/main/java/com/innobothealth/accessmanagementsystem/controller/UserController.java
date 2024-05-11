package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.dto.GetUserDTO;
import com.innobothealth.accessmanagementsystem.dto.UserDTO;
import com.innobothealth.accessmanagementsystem.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<User> registerUser(@RequestBody @Validated UserDTO user) {
        log.info("User Registration for [{}]", user);
        return userService.registerUser(user);
    }

    @GetMapping("getUsers")
    public List<GetUserDTO> getUsers(@NotNull @RequestParam String userType) {
        return userService.getUsers(userType);
    }
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUSer(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
    }
}
