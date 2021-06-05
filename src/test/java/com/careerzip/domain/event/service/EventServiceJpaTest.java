package com.careerzip.domain.event.service;

import com.careerzip.domain.event.entity.Event;
import com.careerzip.domain.event.entity.EventType;
import com.careerzip.domain.event.repository.EventRepository;
import com.careerzip.testconfig.base.BaseServiceJpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventServiceJpaTest extends BaseServiceJpaTest {

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("share link 이벤트 생성 테스트")
    void createShareLinkEventTest() {
        // when
        eventService.createLinkEvent();;

        Event shareEvent = eventRepository.findAll().get(0);

        // then
        assertAll(
                () -> assertThat(shareEvent.getId()).isNotNull(),
                () -> assertThat(shareEvent.getCreatedDateTime()).isNotNull(),
                () -> assertThat(shareEvent.getUpdatedDateTime()).isNotNull(),
                () -> assertThat(shareEvent.getEventType()).isEqualTo(EventType.SHARE_LINK)
        );
    }

}
