package com.iac.service.controller;

import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import com.iac.service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public void EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Post endpoint to handle event creation
     * @param eventDto - event payload
     * @return Event object
     */
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {

        Event response = eventService.create(eventDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
