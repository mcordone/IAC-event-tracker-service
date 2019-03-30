package com.iac.service.service;

import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private static Logger LOGGER = LoggerFactory.getLogger(EventService.class);


    @Autowired
    public EventService() {
    }

    /**
     * create an Event object with data from the Http request object payload
     * @param eventDto
     * @return Event
     */
    public Event create(EventDto eventDto) {

        return new Event();
    }
}
