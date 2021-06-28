package com.sixfold.flightplanner.rest;

import com.sixfold.flightplanner.dto.FlightPlanResponse;
import com.sixfold.flightplanner.model.ConnectionsType;
import com.sixfold.flightplanner.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
@RequiredArgsConstructor
@Api(tags = "Route")
public class RouteRest {

    private final RouteService service;

    @ApiOperation(value = "Get Shortest Route by Distance (in km)")
    @GetMapping("/{from}/{to}")
    public FlightPlanResponse getShortest(@PathVariable String from, @PathVariable String to,
                                          @RequestParam(defaultValue = "NO_AIRPORT_CHANGE") ConnectionsType connection) {
        return service.getShortestRouteByDistance(from, to, connection);
    }

}
