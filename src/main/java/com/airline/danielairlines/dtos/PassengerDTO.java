package com.airline.danielairlines.dtos;

import com.airline.danielairlines.enums.PassengerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDTO {

    private long id;

    @NotBlank(message = "First Name cannot be balnk")
    private String firstName;

    @NotBlank(message = "Last Name cannot be balnk")
    private String lastName;

    private String passportNumber;

    @NotNull(message = "Passenger type cannot be null")
    private PassengerType type;

    private String seatNumber;

    private String specialRequest;
}
