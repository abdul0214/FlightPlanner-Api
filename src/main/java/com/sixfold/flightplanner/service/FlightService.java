package com.sixfold.flightplanner.service;

import com.sixfold.flightplanner.model.Flight;
import com.sixfold.flightplanner.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final DistanceService distanceService;


    public Double getDistance(String from, String to) throws IOException {
        Flight flight_temp = flightRepository.findFlightByFromTo(from, to);
        if (flight_temp != null) {
            return flight_temp.getDistance();
        } else {
            return distanceService.getDistance(from, to);
        }
    }

    public void saveFlight(Flight flight) throws IOException {
        flight.setDistance(getDistance(flight.getFlyFrom(), flight.getFlyTo()));
        flightRepository.save(flight);
    }

    public List<Flight> getShortestFlightByFromTo(String from, String to) {
        return flightRepository.findShortestFlightsByFromTo(from, to);
    }


}
