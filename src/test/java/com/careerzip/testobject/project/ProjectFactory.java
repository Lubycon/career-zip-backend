package com.careerzip.testobject.project;

import com.careerzip.domain.project.entity.Project;

public class ProjectFactory {

    // Hashtag
    public static Project createProject() {
        return Project.builder()
                      .id(1L)
                      .title("Project Title")
                      .build();
    }
}
