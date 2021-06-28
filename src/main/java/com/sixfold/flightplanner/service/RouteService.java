package com.sixfold.flightplanner.service;

import com.sixfold.flightplanner.conf.TequilaApiProperties;
import com.sixfold.flightplanner.dto.FlightPlanAssembler;
import com.sixfold.flightplanner.dto.FlightPlanResponse;
import com.sixfold.flightplanner.dto.TequilaSearchResponse;
import com.sixfold.flightplanner.model.ConnectionsType;
import com.sixfold.flightplanner.model.Flight;
import com.sixfold.flightplanner.model.Route;
import com.sixfold.flightplanner.repository.RouteRepository;
import com.sixfold.flightplanner.service.exception.types.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final TequilaFeignService tequilaFeignService;
    private final FlightService flightService;
    private final FlightPlanAssembler flightPlanAssembler;
    private final TequilaApiProperties tequilaApiProperties;

    @Transactional
    public List<Route> Fetch(String from, String to) {
        TequilaSearchResponse searchResponse = tequilaFeignService.getRoutes(tequilaApiProperties.getApiKey(),
                from, to, tequilaApiProperties.getMaxStopOvers());
        List<Route> routes = searchResponse.getRoutes();
        if (routes.isEmpty()) {
            throw new ResourceNotFound(from, to);
        }
        routes.forEach(route -> {
            saveRouteFlights(route);
            routeRepository.save(setDistances(route));
        });
        return routes;
    }

    public Route setDistances(Route route) {
        route.setFlightsDistance();
        List<String> differentAirportConnections = getConnectionsLocation(route);
        List<Double> airportChangeDistances = new ArrayList<>();
        route.setAirportChangesWithin100Km(true);
        for (int i = 0; i < differentAirportConnections.size(); i = i + 2) {
            try {
                airportChangeDistances.add(flightService.getDistance(differentAirportConnections.get(i), differentAirportConnections.get(i + 1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (route.getHasAirportChange()) {
            route.setAirportChangesWithin100Km(Collections.max(airportChangeDistances) <= 100);
            route.setTotalDistance(route.getTotalDistance() + airportChangeDistances.stream().mapToDouble(f -> f).sum());
        }
        return route;
    }

    public void saveRouteFlights(Route route) {
        List<Flight> flights = route.getRouteFlights();
        flights.forEach(flight -> {
            try {
                flightService.saveFlight(flight);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public FlightPlanResponse getShortestRouteByDistance(String from, String to, ConnectionsType connectionsType) {
        List<Flight> flights = flightService.getShortestFlightByFromTo(from, to);
        if (flights.size() != 0) {
            return flightPlanAssembler.toResponseFromFlight(flights.get(0));
        } else {
            List<Route> routes = getAllRoutes(from, to);
            routes = filterRoutesByAirportChange(routes, connectionsType);
            Collections.sort(routes, Comparator.comparingDouble(route -> route.getTotalDistance()));
            if (!routes.isEmpty()) {
                return flightPlanAssembler.toResponseFromRoute(routes.get(0));
            }
            throw new ResourceNotFound(from, to);
        }
    }

    public List<Route> getAllRoutes(String from, String to) {
        List<Route> routes = routeRepository.findRoutesByFromAndTo(from, to);
        if (routes.size() != 0) {
            return routes;
        } else {
            return Fetch(from, to);
        }
    }

    public List<Route> filterRoutesByAirportChange(List<Route> routes, ConnectionsType connectionsType) {
        routes = routes.stream().filter((route -> route.getAirportChangesWithin100Km() != false)).collect(Collectors.toList());
        if (connectionsType == ConnectionsType.NO_AIRPORT_CHANGE) {
            routes = routes.stream().filter((route -> route.getHasAirportChange() == false)).collect(Collectors.toList());
        } else if (connectionsType == ConnectionsType.ONLY_AIRPORT_CHANGE) {
            routes = routes.stream().filter((route -> route.getHasAirportChange() == true)).collect(Collectors.toList());
        }
        return routes;
    }

    public List<String> getConnectionsLocation(Route route) {
        List<String> airportCodes = new ArrayList<>();
        route.getRouteFlights().forEach(flight -> {
            airportCodes.add(flight.getFlyFrom());
            airportCodes.add(flight.getFlyTo());
        });
        airportCodes.remove(airportCodes.remove(0));
        airportCodes.remove(airportCodes.remove(airportCodes.size() - 1));
        return removeConsecutiveDuplicates(airportCodes);
    }

    public List<String> removeConsecutiveDuplicates(List<String> input) {
        Stack<String> stk = new Stack<>();
        for (String code : input) {
            if (!stk.empty()) {
                if (Objects.equals(code, stk.peek())) stk.pop();
                else stk.push(code);
            } else stk.push(code);
        }
        return new ArrayList(stk);
    }
}
