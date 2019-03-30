package com.iac.service.domain;

import com.iac.service.entity.Event;

import java.util.List;

public class EventCollectionResponse {
    private List<Event> data;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }
}
