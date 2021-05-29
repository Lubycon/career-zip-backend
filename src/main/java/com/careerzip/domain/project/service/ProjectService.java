package com.careerzip.domain.project.service;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.dto.response.archivesresponse.RelatedProject;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final AnswerRepository answerRepository;

    public Set<ProjectSummary> findSelectedProjectsBy(List<QuestionWithAnswers> questionWithAnswers) {
        return questionWithAnswers.stream()
                                  .flatMap(questionWithAnswer -> Stream.of(questionWithAnswer.getAnswers()))
                                  .flatMap(List::stream)
                                  .map(answer -> answer.getProject())
                                  .collect(Collectors.toCollection(LinkedHashSet::new));

    }

    public Set<RelatedProject> findAllRelatedBy(Page<Archive> archivePage) {
        List<Archive> archives = archivePage.getContent();
        List<Answer> answers = answerRepository.findAllByArchives(archives);
        return answers.stream()
                      .map(answer -> {
                          Archive archive = answer.getArchive();
                          Project project = answer.getProject();
                          return RelatedProject.of(project, archive);
                      })
                      .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
