package com.careerzip.domain.archive.dto.response.archivingsresponse;

import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.global.pagination.dto.PageDetails;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ArchivesResponse {

    @NotNull
    private final PageDetails pageDetails;

    @NotNull
    private final List<ArchiveSummary> archives;

    @Builder
    private ArchivesResponse(PageDetails pageDetails, List<ArchiveSummary> archives) {
        this.pageDetails = pageDetails;
        this.archives = archives;
    }

    public static ArchivesResponse of(Page<Archive> page, List<ArchiveSummary> archives) {
        return ArchivesResponse.builder()
                               .pageDetails(PageDetails.from(page))
                               .archives(archives)
                               .build();
    }
}
