package com.sixfold.flightplanner.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long route_id;

    @Column(nullable = false)
    private String flyFrom;

    @Column(nullable = false)
    private String flyTo;

    @Column
    private Double totalDistance;

    @Column
    private Boolean hasAirportChange;

    @Column
    private Boolean airportChangesWithin100Km;

    @ManyToMany
    @UniqueElements
    @JoinTable(
            name = "flight_routes",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private List<Flight> routeFlights;


    @JsonProperty("route")
    public void setFlights(List<Flight> flights) {
        this.routeFlights = flights;
    }

    @JsonProperty("has_airport_change")
    public void hasAirportChange(Boolean change) {
        this.hasAirportChange = change;
    }

    public void setFlightsDistance() {
        this.totalDistance = this.routeFlights.stream().mapToDouble(Flight::getDistance).sum();
    }

}
