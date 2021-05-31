package com.careerzip.controller;

import com.careerzip.domain.questionpaper.dto.LatestQuestionPaperResponse;
import com.careerzip.domain.questionpaper.service.QuestionPaperService;
import com.careerzip.global.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/questionpapers")
public class QuestionPaperController {

    private final QuestionPaperService questionPaperService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiResponse<LatestQuestionPaperResponse> latest() {
        LatestQuestionPaperResponse questionPaper = questionPaperService.findLatest();
        return ApiResponse.success(questionPaper);
    }
}
