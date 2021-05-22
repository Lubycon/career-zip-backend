package com.careerzip.domain.record.dto.response.recorddetailresponse;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.hashtag.dto.response.HashtagDetail;
import com.careerzip.domain.hashtag.entity.Hashtag;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class AnswerDetail {

    @Nullable
    private final String comment;

    @Nullable
    private final HashtagDetail hashtag;

    @Builder
    private AnswerDetail(String comment, HashtagDetail hashtag) {
        this.comment = comment;
        this.hashtag = hashtag;
    }

    // TODO: Hashtag null 값 반환에 대한 처리
    public static AnswerDetail of(Answer answer, Hashtag hashtag) {
        return AnswerDetail.builder()
                           .comment(answer.getComment())
                           .hashtag(HashtagDetail.from(hashtag))
                           .build();
    }
}
