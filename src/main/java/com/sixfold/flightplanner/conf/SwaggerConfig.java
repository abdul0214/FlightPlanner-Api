package com.sixfold.flightplanner.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String REGEX_FLIGHTPLANNER = "/api.+";

    private static ApiInfo apiInfo(String title, String description, String version) {
        return new ApiInfo(title, description, version, null, null, null, null, new ArrayList<>());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("flightPlanner")
                .apiInfo(apiInfo("Flights Planner API", "API documentation", "1.0"))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(REGEX_FLIGHTPLANNER))
                .build();
    }
}