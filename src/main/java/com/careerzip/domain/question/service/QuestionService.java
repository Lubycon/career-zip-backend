package com.careerzip.domain.question.service;

import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letter.entity.Letter;
import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    public List<QuestionWithAnswers> findWithAnswers(Archiving archiving) {
        Letter letter = archiving.getLetter();
        LetterForm letterForm = letter.getLetterForm();
        List<Question> questions = questionRepository.findAllBy(letterForm);
        return answerService.groupingAnswersBy(archiving, questions);
    }
}
