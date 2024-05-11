package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.SMSOTP;
import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.dto.GetUserDTO;
import com.innobothealth.accessmanagementsystem.dto.TokenResponse;
import com.innobothealth.accessmanagementsystem.dto.UserDTO;
import com.innobothealth.accessmanagementsystem.repository.DoctorRepository;
import com.innobothealth.accessmanagementsystem.repository.InsurenceRepository;
import com.innobothealth.accessmanagementsystem.repository.SMSRepository;
import com.innobothealth.accessmanagementsystem.repository.UserRepository;
import com.innobothealth.accessmanagementsystem.service.UserService;
import com.innobothealth.accessmanagementsystem.util.EmailSender;
import com.innobothealth.accessmanagementsystem.util.JWTService;
import com.innobothealth.accessmanagementsystem.util.Role;
import com.innobothealth.accessmanagementsystem.util.SMSSender;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SMSRepository smsRepository;

    private final JWTService jwtService;

    private final SMSSender smsSender;
    private final ModelMapper modelMapper;
    private static final Random random = new Random();

    private final DoctorRepository doctorRepository;
    private final InsurenceRepository insurenceRepository;
    private final EmailSender emailSender;

    public UserServiceImpl(UserRepository userRepository, SMSRepository smsRepository, JWTService jwtService, SMSSender smsSender, ModelMapper modelMapper, DoctorRepository doctorRepository, InsurenceRepository insurenceRepository, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.smsRepository = smsRepository;
        this.jwtService = jwtService;
        this.smsSender = smsSender;
        this.modelMapper = modelMapper;
        this.doctorRepository = doctorRepository;
        this.insurenceRepository = insurenceRepository;
        this.emailSender = emailSender;
    }


    @Override
    public ResponseEntity<User> registerUser(UserDTO user) {
        User userDoc = modelMapper.map(user, User.class);
//        userDoc.setIsVerified(false);
        User save = userRepository.save(userDoc);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @Override
    public ResponseEntity<User> registerAdmin(UserDTO admin) {
        if (userRepository.existsUserByEmail(admin.getEmail())) {
            log.error("Same user already exists! [{}]", admin.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User userDoc = modelMapper.map(admin, User.class);
        userDoc.setRole(admin.getRole());
        userDoc.setIsActivated(true);
        userDoc.setIsMFAEnabled(true);
        userDoc.setIsEmailVerified(false);
        User save = userRepository.save(userDoc);
        emailSender.sendEmail(save.getEmail(), "User Registration | InnobotHealth", "You have been successfully registered to InnobotHealth. Sign in with your email (".concat(save.getEmail()).concat(") and to access the admin portal."));
        smsSender.sendNotification("You have been successfully registered to InnobotHealth. Sign in with your email (".concat(save.getEmail()).concat(save.getEmail()).concat(") and to access the admin portal."), save.getMobileNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username);
            }
        };

    }

    @Override
    public TokenResponse generateToken(String email, String otp) {

        SMSOTP byEmail = smsRepository.findByEmail(email);
        if (byEmail != null && byEmail.getOtp().equals(otp) && byEmail.getExp() > System.currentTimeMillis()) {
            UserDetails user = userRepository.findByEmail(email);
            return TokenResponse.builder().accessToken(jwtService.generateToken(user))
                    .refreshToken(jwtService.generateRefreshToken(user))
                    .userDetails(user)
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP!");

    }

    @Override
    public List<GetUserDTO> getUsers(String userType) {

        List<GetUserDTO> getUserDTOList = new ArrayList<>();
        if (userType.equals(Role.ADMIN.name()) || userType.equals(Role.STAFF.name()) || userType.equals(Role.COORDINATOR.name())) {
            userRepository.findAllByRole(Role.valueOf(userType)).stream().forEach(user -> {
                getUserDTOList.add(GetUserDTO.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build());
            });
            return getUserDTOList;

        }

        if (userType.equals("DOCTOR")) {
            doctorRepository.findAll().stream().forEach(d -> {
                getUserDTOList.add(GetUserDTO.builder()
                        .id(d.getId())
                        .firstName(d.getFirstName())
                        .lastName(d.getLastName())
                        .build());
            });
            return getUserDTOList;
        }

        if (userType.equals("INSURENCE")) {
            insurenceRepository.findAll().stream().forEach(d -> {
                getUserDTOList.add(GetUserDTO.builder()
                        .id(d.getId())
                        .firstName(d.getName())
                        .lastName(null)
                        .build());
            });
            return getUserDTOList;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type!");

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUSer(String id) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        userRepository.delete(byId.get());
    }

    @Override
    public User updateUser(String id, UserDTO user) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }
        User user1 = byId.get();
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setPassword(user.getPassword());
        user1.setMobileNumber(user.getMobileNumber());
        user1.setRole(user.getRole());
        user1.setNotificationPreference(user.getNotificationPreference());
        return userRepository.save(user1);
    }

    @Override
    public void requestOTP(String email, String password) {
        Optional<User> byEmailAndPassword = userRepository.findByEmailAndPassword(email, password);
        if (!byEmailAndPassword.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User!");
        }
        sendOTP(otpGenerator(), byEmailAndPassword.get());
    }

    private String otpGenerator() {
        return String.valueOf(random.nextInt(900000) + 100000);
    }

    private void sendOTP(String otp, User user) {
        SMSOTP byEmail = smsRepository.findByEmail(user.getEmail());
        if (byEmail != null) {
            byEmail.setOtp(otp);
            byEmail.setExp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30);
            smsRepository.save(byEmail);
        } else {
            smsRepository.save(SMSOTP.builder().email(user.getEmail()).otp(otp).exp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30).build());
        }
        smsSender.sendOTP(otp, user.getMobileNumber());
    }
    @Override
    public void deleteUSer(String id) {
    Optional<User> byId = userRepository.findById(id);
    if (!byId.isPresent()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
    }
    userRepository.delete(byId.get());
}
}
