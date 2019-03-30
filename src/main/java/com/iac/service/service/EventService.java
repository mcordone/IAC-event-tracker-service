package com.iac.service.service;

import com.iac.service.domain.EventCollectionResponse;
import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import com.iac.service.exception.NotFoundException;
import com.iac.service.exception.ServiceException;
import com.iac.service.repository.EventRepository;
import com.iac.service.util.EventConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    /**
     *
     * @param id
     * @return Event
     */
    public Event findById(Long id) {

        Event event;

        try {
            event = eventRepository.findEventById(id);

            if(Objects.isNull(event)) {
                throw new NotFoundException(String.format("Event with ID {%d} not found", id));
            }
        }
        catch (DataAccessException e) {
            LOGGER.error("Error finding event with ID {}", id, e);
            throw new ServiceException(String.format("Event with ID {%d} not found", id), e);
        }

        return event;
    }

    /**
     *
     * @return List<Event>
     */
    public EventCollectionResponse findAll() {

        List<Event> eventList;

        try {
            eventList = eventRepository.findAll();
        }
        catch (DataAccessException e) {
            LOGGER.error("Error occurred while retrieving events", e);
            throw new ServiceException(String.format("Error occurred while retrieving events"), e);
        }

        EventCollectionResponse response = new EventCollectionResponse();
        response.setData(eventList);

        return response;
    }
}
