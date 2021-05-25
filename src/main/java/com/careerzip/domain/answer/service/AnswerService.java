package com.careerzip.domain.answer.service;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    public List<QuestionWithAnswers> groupingAnswersBy(Archiving archiving, List<Question> questions) {
        List<Long> questionIds = questions.stream()
                                          .map(Question::getId)
                                          .collect(Collectors.toList());
        List<Answer> answers = answerRepository.findAllBy(archiving, questionIds);
        return QuestionWithAnswers.listOf(questions, answers);
    }
}
