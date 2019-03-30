package com.iac.service.service;

import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import com.iac.service.exception.ServiceException;
import com.iac.service.repository.EventRepository;
import com.iac.service.util.EventConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private static Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * create an Event object with data from the Http request object payload
     * @param eventDto
     * @return Event
     */
    public Event create(EventDto eventDto) {

        Event event;

        try {
            event = eventRepository.save(EventConverter.eventConverter(eventDto));
        }
        catch (DataAccessException e) {
            LOGGER.error("Error occurred when creating event - Reason: ",e.getMessage(), e);
            throw new ServiceException("Error creating event", e);
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Error occurred when creating event - Reason: ",e.getMessage(), e);
            throw new ServiceException("Error creating event, invalid event data provided", e);
        }

        return event;
    }
}
