package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.SMSOTP;
import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.dto.TokenResponse;
import com.innobothealth.accessmanagementsystem.dto.UserDTO;
import com.innobothealth.accessmanagementsystem.repository.SMSRepository;
import com.innobothealth.accessmanagementsystem.repository.UserRepository;
import com.innobothealth.accessmanagementsystem.service.UserService;
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

    public UserServiceImpl(UserRepository userRepository, SMSRepository smsRepository, JWTService jwtService, SMSSender smsSender, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.smsRepository = smsRepository;
        this.jwtService = jwtService;
        this.smsSender = smsSender;
        this.modelMapper = modelMapper;
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
        userDoc.setRole(Role.ADMIN);
        userDoc.setIsActivated(true);
        userDoc.setIsMFAEnabled(true);
        userDoc.setIsEmailVerified(false);
        User save = userRepository.save(userDoc);
        SMSOTP byEmail = smsRepository.findByEmail(admin.getEmail());
        String otp = otpGenerator();
        if (byEmail != null) {
            byEmail.setOtp(otp);
            byEmail.setExp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30);
            smsRepository.save(byEmail);
        } else {
            smsRepository.save(SMSOTP.builder().email(save.getEmail()).otp(otp).exp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30).build());
        }
        smsSender.sendOTP(otp, save.getMobileNumber());
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
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP!");

    }

    private String otpGenerator() {
        return String.valueOf(random.nextInt(900000) + 100000);
    }

}
