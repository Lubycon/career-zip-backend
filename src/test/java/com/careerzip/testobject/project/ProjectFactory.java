package com.careerzip.testobject.project;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.project.dto.request.CreateProjectRequest;
import com.careerzip.domain.project.dto.request.CreateProjectRequestBuilder;
import com.careerzip.domain.project.entity.Project;

import java.time.LocalDate;

import static com.careerzip.testobject.account.AccountFactory.createMember;

public class ProjectFactory {

    // Project
    public static Project createProject() {
        return Project.builder()
                      .id(1L)
                      .title("Project Title")
                      .description("Project Description")
                      .aim("Project Aim")
                      .role("Project Role")
                      .mainBusiness("Main Business")
                      .participantsCount(5)
                      .contribution(50.0)
                      .teamMembers("Member1, Member2, Member3")
                      .account(createMember())
                      .build();
    }

    public static Project createJpaProjectOf(Account account) {
        return Project.builder()
                      .title("Project Title")
                      .description("Project Description")
                      .aim("Project Aim")
                      .role("Project Role")
                      .mainBusiness("Main Business")
                      .participantsCount(5)
                      .contribution(50.0)
                      .teamMembers("Member1, Member2, Member3")
                      .account(account)
                      .build();
    }

    // CreateProjectRequest
    public static CreateProjectRequest createProjectRequest() {
        return CreateProjectRequestBuilder.newBuilder()
                                          .title("New Title")
                                          .description("Description")
                                          .aim("Aim")
                                          .role("Role")
                                          .mainBusiness("Main Business")
                                          .participantsCount(3)
                                          .contribution(70)
                                          .teamMembers("Person1, Person2, Person3")
                                          .startDate(LocalDate.of(2021, 6, 11))
                                          .endDate(LocalDate.of(2021, 6, 12))
                                          .build();
    }
}
