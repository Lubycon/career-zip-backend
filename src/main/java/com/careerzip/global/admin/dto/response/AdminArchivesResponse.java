package com.careerzip.global.admin.dto.response;

import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.global.pagination.dto.PageDetails;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class AdminArchivesResponse {

    @NotNull
    private final PageDetails pageDetails;

    private final List<ArchiveRelatedData> archives;

    @Builder
    private AdminArchivesResponse(PageDetails pageDetails, List<ArchiveRelatedData> archives) {
        this.pageDetails = pageDetails;
        this.archives = archives;
    }

    public static AdminArchivesResponse of(Page<Archive> page, List<ArchiveRelatedData> archives) {
        return AdminArchivesResponse.builder()
                                    .pageDetails(PageDetails.from(page))
                                    .archives(archives)
                                    .build();
    }
}
