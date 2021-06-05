package com.careerzip.domain.event.repository;

import com.careerzip.domain.event.entity.Event;
import com.careerzip.domain.event.entity.EventType;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EventRepositoryTest extends BaseRepositoryTest {

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("EventType을 기준으로 카운트 하는 메서드 테스트")
    void countEventType() {
        // given
        List<Event> events = eventRepository.saveAll(List.of(Event.addShareLink(), Event.addShareLink(), Event.addShareLink()));

        // when
        long shareLinkCount = eventRepository.countAllByEventType(EventType.SHARE_LINK);

        // then
        assertThat(shareLinkCount).isEqualTo(events.size());
    }
}
