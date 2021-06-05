package com.careerzip.domain.event.repository;

import com.careerzip.domain.event.entity.Event;
import com.careerzip.domain.event.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    Long countAllByEventType(EventType eventType);
}
