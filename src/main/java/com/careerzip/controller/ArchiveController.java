package com.careerzip.controller;

import com.careerzip.domain.archiving.dto.response.archivingsresponse.ArchivingsResponse;
import com.careerzip.domain.archiving.service.ArchivingService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.annotation.LoginAccount;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/archives")
public class ArchiveController {

    private final ArchivingService archivingService;

    @GetMapping
    public ApiResponse<ArchivingsResponse> recordList(@LoginAccount OAuthAccount loginAccount,
                                                      @ModelAttribute Pagination pagination) {
        ArchivingsResponse records = archivingService.findAll(loginAccount, pagination);
        return ApiResponse.success(records);
    }

    @GetMapping("/{recordId}")
    public void recordDetail(@LoginAccount OAuthAccount loginAccount, @PathVariable Long recordId) {
        archivingService.findBy(loginAccount, recordId);
    }
}
