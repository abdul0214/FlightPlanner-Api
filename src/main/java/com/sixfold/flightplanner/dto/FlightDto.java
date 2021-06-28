package com.sixfold.flightplanner.dto;

import lombok.Data;

@Data
public class FlightDto {

    private String flyFrom;

    private String flyTo;

    private Double distance;

    private String airline;
}
