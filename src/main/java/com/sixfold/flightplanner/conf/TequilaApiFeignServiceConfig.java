package com.sixfold.flightplanner.conf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixfold.flightplanner.service.TequilaFeignService;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TequilaApiFeignServiceConfig {

    private final TequilaApiProperties tequilaApiProperties;


    @Bean
    public TequilaFeignService getTequilaFeignService() {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
        return Feign.builder()
                .decoder(new JacksonDecoder(objectMapper))
                .target(TequilaFeignService.class, tequilaApiProperties.getHost());
    }
}
