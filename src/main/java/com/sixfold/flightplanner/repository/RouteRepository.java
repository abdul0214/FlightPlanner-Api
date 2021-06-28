package com.sixfold.flightplanner.repository;

import com.sixfold.flightplanner.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("FROM Route r WHERE r.flyFrom=:from and r.flyTo=:to")
    List<Route> findRoutesByFromAndTo(String from, String to);
}
