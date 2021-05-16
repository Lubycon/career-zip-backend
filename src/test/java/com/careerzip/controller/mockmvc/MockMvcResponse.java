package com.careerzip.controller.mockmvc;

import com.careerzip.global.error.response.ErrorCode;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MockMvcResponse {

    // OK
    public static void ok(ResultActions resultActions) throws Exception {
        resultActions.andDo(print())
                     .andExpect(status().isOk())
                     .andExpect(jsonPath("data").isNotEmpty());
    }

    // BAD REQUEST
    public static void badRequest(ResultActions resultActions, ErrorCode errorCode) throws Exception {
        resultActions.andDo(print())
                     .andExpect(status().isBadRequest())
                     .andExpect(jsonPath("statusCode").value(errorCode.getStatusCode()))
                     .andExpect(jsonPath("message").value(errorCode.getMessage()));
    }

    // UNAUTHORIZED
    public static void unAuthorized(ResultActions resultsActions, ErrorCode errorCode) throws Exception {
        resultsActions.andDo(print())
                      .andExpect(status().isUnauthorized())
                      .andExpect(jsonPath("statusCode").value(errorCode.getStatusCode()))
                      .andExpect(jsonPath("message").value(errorCode.getMessage()));
    }
}
