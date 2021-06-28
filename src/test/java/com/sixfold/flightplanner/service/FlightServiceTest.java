package com.sixfold.flightplanner.service;

import com.sixfold.flightplanner.model.Flight;
import com.sixfold.flightplanner.repository.FlightRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
class FlightServiceTest {

    private final FlightRepository flightRepository = Mockito.mock(FlightRepository.class);
    private final DistanceService distanceService = Mockito.mock(DistanceService.class);

    private final FlightService flightService = new FlightService(flightRepository, distanceService);


    @Test
    void getDistanceReturnedFromRepoNotFromDistanceService() throws IOException {
        Flight flightLowDistance = new Flight();
        Flight flightHighDistance = new Flight();
        flightLowDistance.setFlyFrom("A");
        flightLowDistance.setFlyTo("B");
        flightHighDistance.setFlyFrom("A");
        flightHighDistance.setFlyTo("B");
        flightLowDistance.setDistance(2.0);
        flightHighDistance.setDistance(20000.0);
        Mockito.when(flightRepository.findFlightByFromTo("A", "B")).thenReturn(flightLowDistance);
        Mockito.when(distanceService.getDistance("A", "B")).thenReturn(flightHighDistance.getDistance());
        Assertions.assertThat(flightService.getDistance("A", "B")).isEqualTo(flightLowDistance.getDistance());
    }
}