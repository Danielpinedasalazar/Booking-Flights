package com.airline.danielairlines.services;

import com.airline.danielairlines.dtos.LoginRequest;
import com.airline.danielairlines.dtos.LoginResponse;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.dtos.UserDTO;
import com.airline.danielairlines.entities.User;

import java.util.List;

public interface UserService {

    User currentUser();

    Response<?> updateMyAccount(UserDTO userDTO);

    Response<List<UserDTO>> getAllPilots();

    Response<UserDTO> getAccountDetails();
}
