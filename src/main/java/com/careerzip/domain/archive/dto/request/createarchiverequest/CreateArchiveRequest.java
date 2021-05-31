package com.careerzip.domain.archive.dto.request.createarchiverequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateArchiveRequest {

    private long questionPaperId;
    private List<CreateAnswerDetail> answers;

    CreateArchiveRequest(long questionPaperId, List<CreateAnswerDetail> answers) {
        this.questionPaperId = questionPaperId;
        this.answers = answers;
    }
}
