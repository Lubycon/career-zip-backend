package com.careerzip.domain.questionpaper.service;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.questionpaper.dto.LatestQuestionPaperResponse;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.global.error.exception.entity.QuestionPaperNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QuestionPaperService {

    private final QuestionPaperRepository questionPaperRepository;
    private final QuestionRepository questionRepository;

    public LatestQuestionPaperResponse findLatest() {
        QuestionPaper questionPaper = questionPaperRepository.findLatest().orElseThrow(QuestionPaperNotFoundException::new);
        List<Question> questions = questionRepository.findAllBy(questionPaper.getQuestionPaperForm());
        return LatestQuestionPaperResponse.of(questionPaper, questions);
    }
}
