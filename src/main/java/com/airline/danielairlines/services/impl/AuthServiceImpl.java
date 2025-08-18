package com.airline.danielairlines.services.impl;

import com.airline.danielairlines.dtos.LoginRequest;
import com.airline.danielairlines.dtos.LoginResponse;
import com.airline.danielairlines.dtos.RegistrationRequest;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.entities.Role;
import com.airline.danielairlines.entities.User;
import com.airline.danielairlines.enums.AuthMethod;
import com.airline.danielairlines.exceptions.BadRequestException;
import com.airline.danielairlines.exceptions.NotFoundException;
import com.airline.danielairlines.repo.RoleRepo;
import com.airline.danielairlines.repo.UserRepo;
import com.airline.danielairlines.security.JwtUtils;
import com.airline.danielairlines.services.AuthService;
import com.airline.danielairlines.services.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepo roleRepo;
    private final EmailNotificationService emailNotificationService;

    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("Inside Register()");
        if(userRepo.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("Email address already in use");
        }

        List<Role> userRoles;

        if(registrationRequest.getRoles() != null && !registrationRequest.getRoles().isEmpty()) {
            userRoles = registrationRequest.getRoles().stream()
                    .map(roleName -> roleRepo.findByName(roleName.toUpperCase())
                            .orElseThrow(() -> new NotFoundException("Role " + roleName + " NotFound")))
                            .toList();
        }else {
            Role defaultRole = roleRepo.findByName("CUSTOMER")
                    .orElseThrow(() -> new NotFoundException("Role CUSTOMER does not exist"));
            userRoles = List.of(defaultRole);
        }

        User userToSave = new User();
        userToSave.setName(registrationRequest.getName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPhoneNumber(registrationRequest.getPhoneNumber());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setRoles(userRoles);
        userToSave.setCreatedAt(LocalDateTime.now());
        userToSave.setUpdatedAt(LocalDateTime.now());
        userToSave.setProvider(AuthMethod.LOCAL);
        userToSave.setActive(true);

        User savedUser = userRepo.save(userToSave);

        emailNotificationService.sendWelcomeEmail(savedUser);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("user register successfully")
                .build();

    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Inside Login()");

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if(!user.isActive()) {
            throw new BadRequestException("Account is not active, please reach out to a Customer Care...");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new BadRequestException("Incorrect password");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleNames);

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("login successful")
                .data(loginResponse)
                .build();
    }
}
