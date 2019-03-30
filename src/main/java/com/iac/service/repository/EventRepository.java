package com.iac.service.repository;

import com.iac.service.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event save(Event event);
    Event findEventById(Long id);
    List<Event> findAll();
}
