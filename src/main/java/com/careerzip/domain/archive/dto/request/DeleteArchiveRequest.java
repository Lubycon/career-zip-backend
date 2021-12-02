package com.careerzip.domain.archive.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class DeleteArchiveRequest {
    private List<Long> deleteArchiveIds;
}
