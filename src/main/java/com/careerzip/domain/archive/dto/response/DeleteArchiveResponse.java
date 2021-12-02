package com.careerzip.domain.archive.dto.response;

import com.careerzip.domain.archive.dto.response.archivesresponse.ArchivesResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class DeleteArchiveResponse {
    private final int deletedCount;
    private final ArchivesResponse archivesResponse;

}
