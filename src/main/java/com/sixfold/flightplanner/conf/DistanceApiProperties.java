package com.sixfold.flightplanner.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "airport-api")
public class DistanceApiProperties {

    @NotBlank(message = "host url should be present")
    private String url;

    @NotBlank(message = "Tequila userKey should be present")
    private String userKey;

    @NotBlank(message = "distanceToMoon should be present")
    private String distanceToMoon;
}
