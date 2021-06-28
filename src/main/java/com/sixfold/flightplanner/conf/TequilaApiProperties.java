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
@ConfigurationProperties(prefix = "tequila")
public class TequilaApiProperties {

    @NotBlank(message = "Tequila host url should be present")
    private String host;

    @NotBlank(message = "Tequila api key should be present")
    private String apiKey;

    @NotBlank(message = "maxStopOvers should be present")
    private String maxStopOvers;
}
