package com.airline.danielairlines.services;

import com.airline.danielairlines.dtos.LoginRequest;
import com.airline.danielairlines.dtos.LoginResponse;
import com.airline.danielairlines.dtos.RegistrationRequest;
import com.airline.danielairlines.dtos.Response;

public interface AuthService {

  Response<?> register(RegistrationRequest registrationRequest);

  Response<LoginResponse> login(LoginRequest loginRequest);
}
