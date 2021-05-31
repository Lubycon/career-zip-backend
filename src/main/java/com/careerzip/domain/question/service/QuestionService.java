package com.careerzip.domain.question.service;

import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateAnswerDetail;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> findAllBy(Archive archive) {
        QuestionPaper questionPaper = archive.getQuestionPaper();
        QuestionPaperForm questionPaperForm = questionPaper.getQuestionPaperForm();
        return questionRepository.findAllBy(questionPaperForm);
    }

    public Map<Long, Question> findAllMapBy(List<CreateAnswerDetail> archiveAnswers) {
        List<Long> questionIds = archiveAnswers.stream()
                                               .map(CreateAnswerDetail::getQuestionId)
                                               .collect(Collectors.toList());
        List<Question> questions = questionRepository.findAllByIds(questionIds);
        return questions.stream()
                        .collect(Collectors.toMap(Question::getId, Function.identity()));
    }
}
