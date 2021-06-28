package com.sixfold.flightplanner.dto;

import com.sixfold.flightplanner.model.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightAssembler {
    public FlightDto toModel(Flight entity) {
        FlightDto dto = new FlightDto();
        dto.setFlyFrom(entity.getFlyFrom());
        dto.setFlyTo(entity.getFlyTo());
        dto.setDistance(entity.getDistance());
        dto.setAirline(entity.getAirline());
        return dto;
    }

    public List<FlightDto> toModel(List<Flight> entities) {
        if (entities == null) {
            return null;
        }

        List<FlightDto> list = new ArrayList<FlightDto>(entities.size());
        for (Flight flight : entities) {
            list.add(toModel(flight));
        }
        return list;
    }


}
