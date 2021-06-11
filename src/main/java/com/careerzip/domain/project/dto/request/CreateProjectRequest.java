package com.careerzip.domain.project.dto.request;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.project.entity.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateProjectRequest {

    private String title;
    private String description;
    private String aim;
    private String role;
    private String mainBusiness;
    private Integer participantsCount;
    private Double contribution;
    private String teamMembers;
    private LocalDate startDate;
    private LocalDate endDate;

    CreateProjectRequest(String title, String description, String aim, String role, String mainBusiness, Integer participantsCount, Double contribution, String teamMembers, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.aim = aim;
        this.role = role;
        this.mainBusiness = mainBusiness;
        this.participantsCount = participantsCount;
        this.contribution = contribution;
        this.teamMembers = teamMembers;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Project toEntity(Account account) {
        return Project.builder()
                      .title(title)
                      .description(description)
                      .aim(aim)
                      .role(role)
                      .mainBusiness(mainBusiness)
                      .participantsCount(participantsCount)
                      .contribution(contribution)
                      .teamMembers(teamMembers)
                      .startDate(startDate)
                      .endDate(endDate)
                      .account(account)
                      .build();
    }
}
