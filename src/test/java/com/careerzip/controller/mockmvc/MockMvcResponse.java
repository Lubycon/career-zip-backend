package com.careerzip.controller.mockmvc;

import com.careerzip.global.error.response.ErrorCode;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MockMvcResponse {

    // OK
    public static ResultActions ok(ResultActions resultActions) throws Exception {
        return resultActions.andDo(print())
                             .andExpect(status().isOk())
                             .andExpect(jsonPath("data").isNotEmpty());
    }

    // BAD REQUEST
    public static ResultActions badRequest(ResultActions resultActions, ErrorCode errorCode) throws Exception {
        return resultActions.andDo(print())
                            .andExpect(status().isBadRequest())
                            .andExpect(jsonPath("statusCode").value(errorCode.getStatusCode()))
                            .andExpect(jsonPath("message").value(errorCode.getMessage()));
    }
}
