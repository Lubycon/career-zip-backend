package com.careerzip.testobject.template;

import com.careerzip.domain.template.entity.Template;

public class TemplateFactory {

    public static Template createJpaTestTemplate() {
        return Template.from("New Template");
    }
}
