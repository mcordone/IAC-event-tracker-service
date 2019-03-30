package com.iac.service.util;

import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;

import java.util.Objects;

public class EventConverter {

    public static Event eventConverter(EventDto eventDto) {

        if(Objects.isNull(eventDto)) {
            throw new IllegalArgumentException("Invalid event data provided");
        }

        Event event = new Event()
                .withName(eventDto.getName())
                .withType(eventDto.getType())
                .withVersion(eventDto.getVersion())
                .withDeviceType(eventDto.getDeviceType())
                .withDeviceFamily(eventDto.getDeviceFamily())
                .withOs(eventDto.getOs())
                .withCountry(eventDto.getCountry())
                .withCity(eventDto.getCity())
                .withCarrier(eventDto.getCarrier());

        return event;
    }
}
