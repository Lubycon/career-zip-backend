package com.careerzip.testconfig.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@AutoConfigureRestDocs
public class BaseControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;
}
