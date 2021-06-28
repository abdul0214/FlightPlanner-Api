package com.sixfold.flightplanner.dto;

import com.sixfold.flightplanner.model.Flight;
import com.sixfold.flightplanner.model.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FlightPlanAssembler {

    private final FlightAssembler flightAssembler;

    public FlightPlanResponse toResponseFromRoute(Route entity) {
        FlightPlanResponse dto = new FlightPlanResponse();
        dto.setFlyFrom(entity.getFlyFrom());
        dto.setFlyTo(entity.getFlyTo());
        dto.setDistance(entity.getTotalDistance());
        dto.setHasAirportChange(entity.getHasAirportChange());
        dto.setFlights(flightAssembler.toModel(entity.getRouteFlights()));
        return dto;
    }

    public FlightPlanResponse toResponseFromFlight(Flight entity) {
        FlightPlanResponse dto = new FlightPlanResponse();
        dto.setFlyFrom(entity.getFlyFrom());
        dto.setFlyTo(entity.getFlyTo());
        dto.setDistance(entity.getDistance());
        dto.setHasAirportChange(false);
        dto.setFlights(flightAssembler.toModel(Arrays.asList(entity)));
        return dto;
    }
}
