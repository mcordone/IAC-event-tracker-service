package com.iac.service.controller;

import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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
}