package com.careerzip.domain.archive.dto.request;

import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor
public class DeleteArchiveRequest {
    private List<Long> deleteArchiveIds = Lists.newArrayList();
}
