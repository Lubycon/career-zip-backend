package com.careerzip.controller;

import com.careerzip.domain.event.service.EventService;
import com.careerzip.global.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/events")
@RestController
public class EventController {

    private final EventService eventService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/share-link")
    public ApiResponse<Long> shareCount() {
        Long shareCount = eventService.shareCount();
        return ApiResponse.success(shareCount);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/share-link")
    public void shareLink() {
        eventService.createLinkEvent();
    }
}
