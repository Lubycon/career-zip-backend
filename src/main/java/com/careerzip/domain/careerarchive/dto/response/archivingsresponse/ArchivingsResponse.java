package com.careerzip.domain.careerarchive.dto.response.archivingsresponse;

import com.careerzip.domain.careerarchive.entity.CareerArchive;
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
    private final List<ArchivingSummary> archivings;

    @Builder
    private ArchivingsResponse(PageDetails pageDetails, List<ArchivingSummary> archivings) {
        this.pageDetails = pageDetails;
        this.archivings = archivings;
    }

    public static ArchivingsResponse of(Page<CareerArchive> page, List<ArchivingSummary> archivings) {
        return ArchivingsResponse.builder()
                                 .pageDetails(PageDetails.from(page))
                                 .archivings(archivings)
                                 .build();
    }
}
