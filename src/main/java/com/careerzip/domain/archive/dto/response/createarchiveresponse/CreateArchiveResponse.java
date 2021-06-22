package com.careerzip.domain.archive.dto.response.createarchiveresponse;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.entity.Archive;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateArchiveResponse {

    private final long archiveId;
    private final boolean firstArchive;

    @Builder
    private CreateArchiveResponse(long archiveId, boolean firstArchive) {
        this.archiveId = archiveId;
        this.firstArchive = firstArchive;
    }

    public static CreateArchiveResponse of(Archive archive, Account account) {
        //submitCount == 1 : 첫 아카이빙인 경우 submitCount가 증가하여 1 이 됩니다.
        return CreateArchiveResponse.builder()
                                    .archiveId(archive.getId())
                                    .firstArchive(account.getSubmitCount() == 1)
                                    .build();
    }
}
