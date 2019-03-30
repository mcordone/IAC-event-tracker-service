package com.iac.service.util;

import com.iac.service.domain.EventDto;
import com.iac.service.entity.Event;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class EventConverterTest {

    private static final String NAME = "session_start";
    private static final String TYPE = "sessionEvent";
    private static final String VERSION = "1.2";
    private static final String DEVICE_TYPE = "Galaxy 10";
    private static final String DEVICE_FAMILY = "Samsung Galaxy";
    private static final String OS = "Android";
    private static final String COUNTRY = "USA";
    private static final String CITY = "nyc";
    private static final String CARRIER = "Verizon";

    private EventDto eventDto;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        eventDto = new EventDto();
        eventDto.setName(NAME);
        eventDto.setType(TYPE);
        eventDto.setVersion(VERSION);
        eventDto.setDeviceType(DEVICE_TYPE);
        eventDto.setDeviceFamily(DEVICE_FAMILY);
        eventDto.setOs(OS);
        eventDto.setCountry(COUNTRY);
        eventDto.setCity(CITY);
        eventDto.setCarrier(CARRIER);
    }

    @Test
    public void eventConverter_successfulConvertTest() {
        Event event = EventConverter.eventConverter(eventDto);
        assertEquals(NAME, event.getName());
        assertEquals(TYPE, event.getType());
        assertEquals(VERSION, event.getVersion());
        assertEquals(DEVICE_TYPE, event.getDeviceType());
        assertEquals(DEVICE_FAMILY, event.getDeviceFamily());
        assertEquals(OS, event.getOs());
        assertEquals(COUNTRY, event.getCountry());
        assertEquals(CITY, event.getCity());
        assertEquals(CARRIER, event.getCarrier());
    }

    @Test
    public void eventConverter_failConvertWithNullParameterTest() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid event data provided");

        EventConverter.eventConverter(null);
    }
}