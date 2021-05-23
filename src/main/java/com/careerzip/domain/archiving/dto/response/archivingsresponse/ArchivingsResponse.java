package com.careerzip.domain.archiving.dto.response.archivingsresponse;

import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.global.pagination.dto.PageDetails;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ArchivingsResponse {

    @NotNull
    private final PageDetails pageDetails;

    @NotNull
    private final List<ArchivingSummary> records;

    @Builder
    private ArchivingsResponse(PageDetails pageDetails, List<ArchivingSummary> records) {
        this.pageDetails = pageDetails;
        this.records = records;
    }

    public static ArchivingsResponse of(Page<Archiving> page, List<ArchivingSummary> records) {
        return ArchivingsResponse.builder()
                              .pageDetails(PageDetails.from(page))
                              .records(records)
                              .build();
    }
}
