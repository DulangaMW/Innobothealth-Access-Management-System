package com.innobothealth.accessmanagementsystem.dto;

import com.innobothealth.accessmanagementsystem.util.Notification;
import com.innobothealth.accessmanagementsystem.util.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String mobileNumber;
    @NotNull
    private Role role;
    @NotNull
    private List<Notification> notificationPreference;

}
