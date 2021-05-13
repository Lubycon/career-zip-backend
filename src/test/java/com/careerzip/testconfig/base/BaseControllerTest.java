package com.careerzip.testconfig.base;

import com.careerzip.testconfig.security.SecurityTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;

@Import(SecurityTestConfig.class)
@AutoConfigureRestDocs
public class BaseControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;
}
