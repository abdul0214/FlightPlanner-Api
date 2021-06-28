package com.sixfold.flightplanner.repository;

import com.sixfold.flightplanner.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("FROM Flight f WHERE f.id = (SELECT min(f.id) FROM Flight f WHERE f.flyFrom=:from and f.flyTo=:to)")
    Flight findFlightByFromTo(String from, String to);


    @Query("FROM Flight f WHERE f.distance = (SELECT min(f.distance) FROM Flight f WHERE f.flyFrom=:from and f.flyTo=:to)")
    List<Flight> findShortestFlightsByFromTo(String from, String to);
}
