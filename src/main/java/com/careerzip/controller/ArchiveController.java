package com.careerzip.controller;

import com.careerzip.domain.archive.dto.response.archivedetailresponse.ArchiveDetailResponse;
import com.careerzip.domain.archive.dto.response.archivingsresponse.ArchivingsResponse;
import com.careerzip.domain.archive.service.ArchiveService;
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

    private final ArchiveService archiveService;

    @GetMapping
    public ApiResponse<ArchivingsResponse> archivingList(@LoginAccount OAuthAccount loginAccount,
                                                         @ModelAttribute Pagination pagination) {
        ArchivingsResponse archivings = archiveService.findAll(loginAccount, pagination);
        return ApiResponse.success(archivings);
    }

    @GetMapping("/{archivingId}")
    public ApiResponse<ArchiveDetailResponse> archivingDetail(@LoginAccount OAuthAccount loginAccount,
                                                              @PathVariable Long archivingId) {
        ArchiveDetailResponse archiving = archiveService.findBy(loginAccount, archivingId);
        return ApiResponse.success(archiving);
    }
}
