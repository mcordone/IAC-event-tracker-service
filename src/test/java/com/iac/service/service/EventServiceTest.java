package com.iac.service.service;

import com.iac.service.domain.EventCollectionResponse;
import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import com.iac.service.exception.NotFoundException;
import com.iac.service.exception.ServiceException;
import com.iac.service.repository.EventRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private static final Long EVENT_ID = 1L;
    private static final String NAME = "session_start";
    private static final String TYPE = "sessionEvent";
    private static final String VERSION = "1.2";
    private static final String DEVICE_TYPE = "Galaxy 10";
    private static final String DEVICE_FAMILY = "Samsung Galaxy";
    private static final String OS = "Android";
    private static final String COUNTRY = "USA";
    private static final String CITY = "nyc";
    private static final String CARRIER = "Verizon";

    @Mock
    private EventRepository eventRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private EventService objectUnderTest;
    private Event event;
    private EventDto eventDto;

    @Before
    public void setUp() throws Exception {

        objectUnderTest = new EventService(eventRepository);

        event = new Event()
                .withId(EVENT_ID)
                .withName(NAME)
                .withType(TYPE)
                .withVersion(VERSION)
                .withDeviceType(DEVICE_TYPE)
                .withDeviceFamily(DEVICE_FAMILY)
                .withOs(OS)
                .withCountry(COUNTRY)
                .withCity(CITY)
                .withCarrier(CARRIER);

        eventDto = new EventDto();
    }

    @Test
    public void whenValidEvent_thenCreateSuccessfulEventTest() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event response = objectUnderTest.create(eventDto);
        assertNotNull(response);
        assertEquals(NAME, response.getName());

        verify(eventRepository).save(any(Event.class));
    }

    @Test
    public void whenDatabaseThrowException_thenEventCreationThrowExceptionTest() {
        when(eventRepository.save(any(Event.class))).thenThrow(DataRetrievalFailureException.class);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Error creating event");

        objectUnderTest.create(eventDto);
    }

    @Test
    public void whenValidEventIsReturn_thenEventLookupSuccessfulTest() {
        when(eventRepository.findEventById(any(Long.class))).thenReturn(event);

        Event event = objectUnderTest.findById(EVENT_ID);
        assertEquals(EVENT_ID, event.getId());
        assertEquals(NAME, event.getName());
        assertEquals(TYPE, event.getType());
        assertEquals(VERSION, event.getVersion());

        verify(eventRepository).findEventById(any(Long.class));
    }

    @Test
    public void whenEventLookupReturnNull_thenEventLookupThrowExceptionTest() {
        when(eventRepository.findEventById(any(Long.class))).thenReturn(null);

        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("Event with ID {1} not found");

        objectUnderTest.findById(EVENT_ID);
    }

    @Test
    public void whenLookupSuccessful_thenFindEventsReturnListTest() {
        List<Event> events = new ArrayList<>(Arrays.asList(event, event));

        when(eventRepository.findAll()).thenReturn(events);

        EventCollectionResponse result = objectUnderTest.findAll();
        assertNotNull(result);
        assertTrue(result.getData().size() == 2);
        assertEquals(EVENT_ID, result.getData().get(0).getId());
        assertEquals(NAME, result.getData().get(0).getName());
        assertEquals(TYPE, result.getData().get(0).getType());
        assertEquals(VERSION, result.getData().get(0).getVersion());

        assertEquals(EVENT_ID, result.getData().get(1).getId());
        assertEquals(NAME, result.getData().get(1).getName());
        assertEquals(TYPE, result.getData().get(1).getType());
        assertEquals(VERSION, result.getData().get(1).getVersion());

        verify(eventRepository).findAll();
    }

    @Test
    public void whenEventLookupReturn_thenEventLookupThrowExceptionTest() {
        when(eventRepository.findAll()).thenThrow(DataRetrievalFailureException.class);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Error occurred while retrieving events");

        objectUnderTest.findAll();
    }
}