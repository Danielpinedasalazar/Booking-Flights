package com.airline.danielairlines.enums;

public enum City {

    //Colombia
    MEDELLIN(Country.COLOMBIA),
    BOGOTA(Country.COLOMBIA),

    //Usa
    MIAMI(Country.USA),
    DALLAS(Country.USA),

    //Uk
    LONDON(Country.UK),
    LEEDS(Country.UK);

    private final Country country;

    City(Country country) {
        this.country = country;
    }
}
