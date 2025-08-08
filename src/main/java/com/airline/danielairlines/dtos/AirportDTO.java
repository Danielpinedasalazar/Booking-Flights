package com.airline.danielairlines.dtos;

import com.airline.danielairlines.enums.City;
import com.airline.danielairlines.enums.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportDTO {

    private long id;

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "City is required")
    private City city;

    @NotNull(message = "Country is required")
    private Country country;

    @NotBlank(message = "iata code is required")
    private String iataCode;
}
