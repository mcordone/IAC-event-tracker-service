package com.iac.service.controller;

import com.iac.service.domain.EventCollectionResponse;
import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import com.iac.service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     *
     * @param id
     * @return Event entity
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    /**
     *
     * @return list of events entities or empty list
     */
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllEvents() {
        EventCollectionResponse events = eventService.findAll();

        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
