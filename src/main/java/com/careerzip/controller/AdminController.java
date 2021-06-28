package com.careerzip.controller;

import com.careerzip.global.admin.dto.request.DateParameters;
import com.careerzip.global.admin.dto.response.*;
import com.careerzip.global.admin.service.AdminService;
import com.careerzip.global.admin.service.NewsLetterService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.global.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final NewsLetterService newsLetterService;

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

    @GetMapping("/questionpapers")
    public ApiResponse<List<AdminQuestionPaperDetail>> findAllQuestionPapers() {
        List<AdminQuestionPaperDetail> questionPapers = adminService.findAllQuestionPapers();
        return ApiResponse.success(questionPapers);
    }

    @GetMapping("/news-letter/campaigns")
    public ApiResponse<CampaignsResponse> findAllCampaigns() {
        CampaignsResponse campaigns = newsLetterService.findAllCampaigns();
        return ApiResponse.success(campaigns);
    }

    @PostMapping("/news-letter/campaigns/reminders")
    public void addRemindersCampaign() {
        newsLetterService.addRemindersCampaign();
    }

    @PostMapping("/news-letter/campaigns/main/contacts")
    public ApiResponse<List<ContactSummary>> addContactsToMainCampaign() {
        List<ContactSummary> contacts = newsLetterService.addContactsToMainCampaign();
        return ApiResponse.success(contacts);
    }

    @GetMapping("/news-letter/contacts/not-archived")
    public ApiResponse<NotArchivedContactsResponse> findAllNotArchivedContacts() {
        NotArchivedContactsResponse contacts = newsLetterService.findAllNotArchivedContacts();
        return ApiResponse.success(contacts);
    }
}
