package com.careerzip.controller;

import com.careerzip.domain.project.dto.request.CreateProjectRequest;
import com.careerzip.domain.project.dto.response.ProjectDetail;
import com.careerzip.domain.project.dto.response.ProjectSummaryResponse;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.project.service.ProjectService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.security.oauth.annotation.LoginAccount;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiResponse<List<ProjectSummaryResponse>> projects(@LoginAccount OAuthAccount loginAccount) {
        List<ProjectSummaryResponse> projects = projectService.findAll(loginAccount);
        return ApiResponse.success(projects);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Long> create(@LoginAccount OAuthAccount loginAccount,
                                    @RequestBody CreateProjectRequest request) {
        Project project = projectService.createBy(loginAccount, request);
        return ApiResponse.success(project.getId());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ApiResponse<ProjectDetail> detail(@LoginAccount OAuthAccount loginAccount, @PathVariable Long id) {
        ProjectDetail project = projectService.findBy(loginAccount, id);
        return ApiResponse.success(project);
    }
}
