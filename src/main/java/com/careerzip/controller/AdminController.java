package com.careerzip.controller;

import com.careerzip.global.admin.dto.request.DateParameters;
import com.careerzip.global.admin.dto.response.AdminArchiveResponse;
import com.careerzip.global.admin.dto.response.AdminArchivesResponse;
import com.careerzip.global.admin.service.AdminService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.global.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/archives")
    public ApiResponse<AdminArchivesResponse> findAllArchives(@ModelAttribute Pagination pagination,
                                                              @ModelAttribute DateParameters dateParameters) {
        AdminArchivesResponse archives = adminService.findAllArchives(pagination, dateParameters);
        return ApiResponse.success(archives);
    }

    @GetMapping("/archives/{archiveId}")
    public ApiResponse<AdminArchiveResponse> findArchive(@PathVariable Long archiveId) {
        AdminArchiveResponse archive = adminService.findArchiveBy(archiveId);
        return ApiResponse.success(archive);
    }
}
