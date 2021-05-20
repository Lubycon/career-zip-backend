package com.careerzip.domain.record.dto.response;

import com.careerzip.domain.record.entity.Record;
import com.careerzip.global.pagination.dto.PageDetails;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class RecordsResponse {

    @NotNull
    private final PageDetails pageDetails;

    @NotNull
    private final List<RecordSummary> records;

    @Builder
    private RecordsResponse(PageDetails pageDetails, List<RecordSummary> records) {
        this.pageDetails = pageDetails;
        this.records = records;
    }

    public static RecordsResponse of(Page<Record> page, List<RecordSummary> records) {
        return RecordsResponse.builder()
                              .pageDetails(PageDetails.from(page))
                              .records(records)
                              .build();
    }
}
