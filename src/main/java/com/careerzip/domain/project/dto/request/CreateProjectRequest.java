package com.careerzip.domain.project.dto.request;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.project.entity.Project;
import lombok.Getter;

import java.time.LocalDate;

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
