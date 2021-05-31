package com.careerzip.controller;

import com.careerzip.domain.project.dto.response.ProjectSummaryResponse;
import com.careerzip.domain.project.service.ProjectService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.security.oauth.annotation.LoginAccount;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
