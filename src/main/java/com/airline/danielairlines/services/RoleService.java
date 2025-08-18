package com.airline.danielairlines.services;

import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.dtos.RoleDTO;

import java.util.List;

public interface RoleService {

    Response<?> createRole(RoleDTO roleDTO);
    Response<?> updateRole(RoleDTO roleDTO);
    Response<List<RoleDTO>> getAllRoles();

}
