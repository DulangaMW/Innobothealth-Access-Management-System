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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;

@Service
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User userDoc = modelMapper.map(admin, User.class);
        userDoc.setRole(Role.ADMIN.name());
        userDoc.setIsActivated(true);
        userDoc.setIsMFAEnabled(true);
        userDoc.setIsEmailVerified(false);
        User save = userRepository.save(userDoc);
        SMSOTP smsotp = smsRepository.save(SMSOTP.builder().email(save.getEmail()).otp(otpGenerator()).exp(System.currentTimeMillis() + 1000 * 60 * 10).build());
        smsSender.sendOTP(smsotp.getOtp(), save.getMobileNumber());
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

        Optional<SMSOTP> byId = smsRepository.findById(email);
        if (byId.isPresent() && byId.get().getOtp().equals(otp) && byId.get().getExp() > System.currentTimeMillis()) {
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
