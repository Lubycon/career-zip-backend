package com.careerzip.testobject.hashtag;

import com.careerzip.domain.hashtag.entity.Hashtag;

public class HashtagFactory {

    // Hashtag
    public static Hashtag createHashtag() {
        return Hashtag.builder()
                      .id(1L)
                      .title("Hashtag Title")
                      .build();
    }
}
