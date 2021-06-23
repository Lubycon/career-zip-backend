package com.careerzip.controller;

import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateArchiveRequest;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ArchiveDetailResponse;
import com.careerzip.domain.archive.dto.response.archivesresponse.ArchivesResponse;
import com.careerzip.domain.archive.dto.response.newarchiveresponse.NewArchiveResponse;
import com.careerzip.domain.archive.service.ArchiveService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.annotation.LoginAccount;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/archives")
public class ArchiveController {

    private final ArchiveService archiveService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiResponse<ArchivesResponse> archiveList(@LoginAccount OAuthAccount loginAccount,
                                                     @ModelAttribute Pagination pagination) {
        ArchivesResponse archives = archiveService.findAll(loginAccount, pagination);
        return ApiResponse.success(archives);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{archiveId}")
    public ApiResponse<ArchiveDetailResponse> archiveDetail(@LoginAccount OAuthAccount loginAccount,
                                                            @PathVariable Long archiveId) {
        ArchiveDetailResponse archive = archiveService.findBy(loginAccount, archiveId);
        return ApiResponse.success(archive);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<NewArchiveResponse> createArchive(@LoginAccount OAuthAccount loginAccount,
                                                         @RequestBody CreateArchiveRequest request) {
        NewArchiveResponse response = archiveService.createBy(loginAccount, request);
        return ApiResponse.success(response);
    }
}
