package com.sixfold.flightplanner.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DistanceResponse {

    private String distance;

    private String units;

    private String authorisedAPI;

    private String processingDurationMillis;

    private String success;

}
