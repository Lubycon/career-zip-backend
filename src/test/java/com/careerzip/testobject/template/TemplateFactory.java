package com.careerzip.testobject.template;

import com.careerzip.domain.template.entity.Template;

public class TemplateFactory {

    // Template
    public static Template createTemplate() {
        return Template.builder()
                       .id(1L)
                       .title("Template title")
                       .build();
    }

    public static Template createJpaTestTemplate() {
        return Template.from("New Template");
    }
}
