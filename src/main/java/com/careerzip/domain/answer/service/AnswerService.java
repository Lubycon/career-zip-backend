package com.careerzip.domain.answer.service;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    public List<QuestionWithAnswers> groupingAnswersBy(Archive archive, List<Question> questions) {
        List<Long> questionIds = questions.stream()
                                          .map(Question::getId)
                                          .collect(Collectors.toList());
        List<Answer> answers = answerRepository.findAllBy(archive, questionIds);
        return QuestionWithAnswers.listOf(questions, answers);
    }
}
