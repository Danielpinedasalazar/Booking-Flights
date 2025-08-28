package com.airline.danielairlines.services.impl;

import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.dtos.RoleDTO;
import com.airline.danielairlines.entities.Role;
import com.airline.danielairlines.exceptions.NotFoundException;
import com.airline.danielairlines.repo.RoleRepo;
import com.airline.danielairlines.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;

    @Override
    public Response<?> createRole(RoleDTO roleDTO) {
        log.info("Inside CreateRole()");
        Role role = modelMapper.map(roleDTO, Role.class);
        role.setName(role.getName().toUpperCase());
        roleRepo.save(role);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role created successfully")
                .build();
    }

    @Override
    public Response<?> updateRole(RoleDTO roleDTO) {
        log.info("Inside UpdateRole()");

        Long id = roleDTO.getId();

        Role existingRole = roleRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        existingRole.setName(roleDTO.getName().toUpperCase());
        roleRepo.save(existingRole);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role updated successfully")
                .build();
    }

    @Override
    public Response<List<RoleDTO>> getAllRoles() {
        log.info("Inside GetAllRoles()");
        List<RoleDTO> roles = roleRepo.findAll().stream()
                .map(role -> modelMapper.map(role, RoleDTO.class)).
                toList();

        return Response.<List<RoleDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(roles.isEmpty() ? "No roles found" : "Roles retrieved successfully")
                .data(roles)
                .build();
    }
}
