package com.careerzip.domain.archive.dto.request;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class DeleteArchiveRequest {
    private List<Long> deleteArchiveIds;
}
