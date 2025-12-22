package com.airline.danielairlines.enums;

import lombok.Getter;

@Getter
public enum City {

    //Colombia
    BOGOTA(Country.COL),
    MEDELLIN(Country.COL),

    //Usa
    MIAMI(Country.USA),
    DALLAS(Country.USA),
    WASHINGTON(Country.USA),

    //Uk
    LONDON(Country.UK),
    LEEDS(Country.UK);

    private final Country country;

    City(Country country) {
        this.country = country;
    }
}
