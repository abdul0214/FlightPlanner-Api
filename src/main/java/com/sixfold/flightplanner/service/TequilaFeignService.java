package com.sixfold.flightplanner.service;

import com.sixfold.flightplanner.dto.TequilaSearchResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TequilaFeignService {

    @Headers("apikey: {apiKey}")
    @RequestLine("GET /search?fly_from=airport:{from}&fly_to=airport:{to}&max_stopovers={maxStopovers}&conn_on_diff_airport=1")
    TequilaSearchResponse getRoutes(@Param("apiKey") String apiKey,
                                    @Param("from") String from,
                                    @Param("to") String to,
                                    @Param("maxStopovers") String maxStopovers);
}
