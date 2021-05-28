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
    public ApiResponse<ArchivingsResponse> archiveList(@LoginAccount OAuthAccount loginAccount,
                                                         @ModelAttribute Pagination pagination) {
        ArchivingsResponse archives = archiveService.findAll(loginAccount, pagination);
        return ApiResponse.success(archives);
    }

    @GetMapping("/{archiveId}")
    public ApiResponse<ArchiveDetailResponse> archiveDetail(@LoginAccount OAuthAccount loginAccount,
                                                            @PathVariable Long archiveId) {
        ArchiveDetailResponse archive = archiveService.findBy(loginAccount, archiveId);
        return ApiResponse.success(archive);
    }
}
