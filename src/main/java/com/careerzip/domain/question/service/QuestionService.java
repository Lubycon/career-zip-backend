package com.careerzip.domain.question.service;

import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.careerarchive.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
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

    public List<QuestionWithAnswers> findWithAnswers(CareerArchive careerArchive) {
        QuestionPaper questionPaper = careerArchive.getQuestionPaper();
        QuestionPaperForm questionPaperForm = questionPaper.getQuestionPaperForm();
        List<Question> questions = questionRepository.findAllBy(questionPaperForm);
        return answerService.groupingAnswersBy(careerArchive, questions);
    }
}
