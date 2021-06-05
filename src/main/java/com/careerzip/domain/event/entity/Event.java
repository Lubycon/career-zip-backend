package com.careerzip.domain.event.entity;

import com.careerzip.global.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    public Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    public EventType eventType;

    private Event(EventType eventType) {
        this.eventType = eventType;
    }

    public static Event addShareLink() {
        return new Event(EventType.SHARE_LINK);
    }
}
