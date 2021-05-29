package com.careerzip.controller;

import com.careerzip.domain.answer.dto.request.PreviousAnswersRequest;
import com.careerzip.domain.answer.dto.response.PreviousAnswersWithQuestion;
import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.security.oauth.annotation.LoginAccount;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/answers")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/previous")
    public ApiResponse<List<PreviousAnswersWithQuestion>> previousAnswers(@LoginAccount OAuthAccount loginAccount,
                                                                          @RequestBody PreviousAnswersRequest previousAnswersRequest) {
        List<PreviousAnswersWithQuestion> previousAnswers = answerService.findAllPreviousBy(loginAccount, previousAnswersRequest);
        return ApiResponse.success(previousAnswers);
    }
}
