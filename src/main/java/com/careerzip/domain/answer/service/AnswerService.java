package com.careerzip.domain.answer.service;

import com.careerzip.domain.answer.dto.request.PreviousAnswersRequest;
import com.careerzip.domain.answer.dto.response.PreviousAnswersWithQuestion;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.global.error.exception.entity.QuestionPaperNotFoundException;
import com.careerzip.security.oauth.dto.OAuthAccount;
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
    private final QuestionRepository questionRepository;
    private final QuestionPaperRepository questionPaperRepository;

    public List<QuestionWithAnswers> groupingAnswersBy(Archive archive, List<Question> questions) {
        List<Answer> answers = answerRepository.findAllBy(archive, questions);
        return QuestionWithAnswers.listOf(questions, answers);
    }

    public List<PreviousAnswersWithQuestion> findAllPreviousBy(OAuthAccount loginAccount, PreviousAnswersRequest previousAnswersRequest) {
        QuestionPaper questionPaper = questionPaperRepository.findLatest().orElseThrow(QuestionPaperNotFoundException::new);
        List<Question> questions = questionRepository.findAllBy(questionPaper.getQuestionPaperForm());
        List<Long> answerIds = answerRepository.findAllPreviousIdsBy(loginAccount.getId(), previousAnswersRequest.getProjectIds(), questions);
        List<Answer> previousAnswers = answerRepository.findAllBy(answerIds);
        return PreviousAnswersWithQuestion.listOf(questions, previousAnswers);
    }
}
