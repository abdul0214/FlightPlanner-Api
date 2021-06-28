package com.sixfold.flightplanner.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlightPlanResponse {

    private String flyFrom;

    private String flyTo;

    private Double distance;

    private Boolean hasAirportChange;

    private List<FlightDto> flights;

}
