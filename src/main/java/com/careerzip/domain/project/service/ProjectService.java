package com.careerzip.domain.project.service;

import com.careerzip.domain.archive.dto.response.archivedetailresponse.AnswerDetail;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProjectService {

    public Set<ProjectSummary> findSelectedProjectsBy(List<QuestionWithAnswers> questionWithAnswers) {
        return questionWithAnswers.stream()
                                  .flatMap(questionWithAnswer -> Stream.of(questionWithAnswer.getAnswers()))
                                  .flatMap(List::stream)
                                  .map(answer -> answer.getProject())
                                  .collect(Collectors.toCollection(LinkedHashSet::new));

    }
}
