package com.careerzip.domain.event.service;

import com.careerzip.domain.event.entity.Event;
import com.careerzip.domain.event.entity.EventType;
import com.careerzip.domain.event.repository.EventRepository;
import com.careerzip.global.api.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Getter
@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;

    public Long shareCount() {
        return eventRepository.countAllByEventType(EventType.SHARE_LINK);
    }

    @Transactional
    public void createLinkEvent() {
        Event shareEvent = Event.addShareLink();
        eventRepository.save(shareEvent);
    }
}
