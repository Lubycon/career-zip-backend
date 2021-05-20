package com.careerzip.controller;

import com.careerzip.domain.record.dto.response.RecordsResponse;
import com.careerzip.domain.record.service.RecordService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.annotation.LoginAccount;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/archives")
public class ArchiveController {

    private final RecordService recordService;

    @GetMapping
    public ApiResponse<RecordsResponse> recordList(@LoginAccount OAuthAccount loginAccount,
                                                   @ModelAttribute Pagination pagination) {
        RecordsResponse records = recordService.findAll(loginAccount, pagination);
        return ApiResponse.success(records);
    }
}
