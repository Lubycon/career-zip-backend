package com.careerzip.domain.account.dto.response;

import com.careerzip.domain.archive.entity.Archive;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountArchiveExist {

    private final boolean archived;
    private final Long id;

    @Builder
    public AccountArchiveExist(boolean archived, Long id) {
        this.archived = archived;
        this.id = id;
    }

    public static AccountArchiveExist hasArchived(Archive archive) {
        return AccountArchiveExist.builder()
                             .archived(true)
                             .id(archive.getId())
                             .build();
    }

    public static AccountArchiveExist notArchived() {
        return AccountArchiveExist.builder()
                             .archived(false)
                             .id(null)
                             .build();
    }
}
