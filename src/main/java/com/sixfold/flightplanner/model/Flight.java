package com.sixfold.flightplanner.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@SuperBuilder
@Entity
@Getter
@Setter
@ToString
@Table(name = "flights")
public class Flight {

    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String flyFrom;

    @Column(nullable = false)
    private String flyTo;

    @Column
    private Double distance;

    private String airline;

    @ToString.Exclude
    @ManyToMany(mappedBy = "routeFlights")
    private List<Route> routes;

}