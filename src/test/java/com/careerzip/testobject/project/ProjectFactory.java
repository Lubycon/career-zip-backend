package com.careerzip.testobject.project;

import com.careerzip.domain.project.entity.Project;

import static com.careerzip.testobject.account.AccountFactory.createMember;

public class ProjectFactory {

    // Hashtag
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
}
