package com.iac.service.controller;

import com.iac.service.domain.EventCollectionResponse;
import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerTest {

    private static final String NAME = "session_start";
    private static final String TYPE = "sessionEvent";
    private static final String VERSION = "1.2";
    private static final String DEVICE_TYPE = "Galaxy 10";
    private static final String DEVICE_FAMILY = "Samsung Galaxy";
    private static final String OS = "Android";
    private static final int EVENT_LENGTH = 2;

    @LocalServerPort
    private int port;

    private URI baseUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    private EventDto eventDto;

    @Before
    public void setUp() throws Exception {
        this.baseUrl = new URI("http://localhost:" + port + "/event/");

        eventDto = new EventDto()
                .withName(NAME)
                .withType(TYPE)
                .withVersion(VERSION)
                .withDeviceType(DEVICE_TYPE)
                .withDeviceFamily(DEVICE_FAMILY)
                .withDeviceFamily(DEVICE_FAMILY)
                .withOs(OS);
    }

    @Test
    public void createEvent() {
        ResponseEntity<Event> response = restTemplate.postForEntity(baseUrl.toString(), eventDto, Event.class);
        assertNotNull(response);
        assertEquals(NAME, response.getBody().getName());
        assertEquals(TYPE, response.getBody().getType());
        assertEquals(VERSION, response.getBody().getVersion());
        assertEquals(DEVICE_TYPE, response.getBody().getDeviceType());
    }

    @Test
    public void findEventById() {
        //create event
        ResponseEntity<Event> response = restTemplate.postForEntity(baseUrl.toString(), eventDto, Event.class);
        assertNotNull(response);

        //retrieve event id
        Long eventId = response.getBody().getId();

        //retrieve event object
        String url = baseUrl.toString() + "/" + eventId;
        response = restTemplate.getForEntity(url, Event.class);
        assertEquals(eventId, response.getBody().getId());
    }

    @Test
    public void findEventByIdReturns404StatusCodeWhenEventDontExist() {

        int nonExistingEventId = -1;

        //retrieve event object
        String url = baseUrl.toString() + "/" + nonExistingEventId;
        ResponseEntity<Event> response = restTemplate.getForEntity(url, Event.class);
        assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void findAllEvents() {
        //create first event
        ResponseEntity<Event> response = restTemplate.postForEntity(baseUrl.toString(), eventDto, Event.class);
        assertNotNull(response);
        Long firstEventId = response.getBody().getId();

        //create second event
        response = restTemplate.postForEntity(baseUrl.toString(), eventDto, Event.class);
        assertNotNull(response);
        Long secondEventId = response.getBody().getId();

        //retrieve all
        EventCollectionResponse result = restTemplate.getForObject(baseUrl.toString(), EventCollectionResponse.class);
        assertNotNull(result);
        assertEquals(EVENT_LENGTH, result.getData().size());
        assertEquals(firstEventId, result.getData().get(0).getId());
        assertEquals(secondEventId, result.getData().get(1).getId());
    }
}