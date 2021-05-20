package com.careerzip.domain.hashtag.dto.response;

import com.careerzip.domain.hashtag.entity.Hashtag;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class HashtagDetail {

    private long id;

    @Nullable
    private String title;

    @Builder
    private HashtagDetail(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static HashtagDetail from(Hashtag hashtag) {
        return HashtagDetail.builder()
                            .id(hashtag.getId())
                            .title(hashtag.getTitle())
                            .build();
    }
}
