package com.airline.danielairlines.dtos;

import com.airline.danielairlines.entities.Role;
import com.airline.danielairlines.enums.AuthMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean emailVerified;

    private AuthMethod provider;

    private String providerId;

    private List<Role> roles;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
