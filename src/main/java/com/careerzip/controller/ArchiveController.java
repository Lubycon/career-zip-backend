package com.careerzip.controller;

import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.ArchivingDetailResponse;
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
    public ApiResponse<ArchivingsResponse> archivingList(@LoginAccount OAuthAccount loginAccount,
                                                         @ModelAttribute Pagination pagination) {
        ArchivingsResponse archivings = archivingService.findAll(loginAccount, pagination);
        return ApiResponse.success(archivings);
    }

    @GetMapping("/{archivingId}")
    public ApiResponse<ArchivingDetailResponse> archivingDetail(@LoginAccount OAuthAccount loginAccount,
                                                                @PathVariable Long archivingId) {
        ArchivingDetailResponse archiving = archivingService.findBy(loginAccount, archivingId);
        return ApiResponse.success(archiving);
    }
}
