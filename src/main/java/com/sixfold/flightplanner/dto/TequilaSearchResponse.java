package com.sixfold.flightplanner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sixfold.flightplanner.model.Route;
import lombok.Data;

import java.util.List;

@Data
public class TequilaSearchResponse {

    private String currency;

    private List<Route> routes;

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    public void setRoutes(List<Route> routes) throws JsonProcessingException {
        this.routes = routes;
    }
}
