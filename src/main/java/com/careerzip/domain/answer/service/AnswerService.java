package com.careerzip.domain.answer.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.answer.dto.request.PreviousAnswersRequest;
import com.careerzip.domain.answer.dto.response.PreviousAnswersWithQuestion;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateAnswerDetail;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.project.entity.Project;
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
import java.util.Map;
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
        QuestionPaper questionPaper = questionPaperRepository.findById(previousAnswersRequest.getQuestionPaperId())
                                                             .orElseThrow(QuestionPaperNotFoundException::new);
        List<Question> questions = questionRepository.findAllBy(questionPaper.getQuestionPaperForm());
        List<Long> answerIds = answerRepository.findAllPreviousIdsBy(loginAccount.getId(), previousAnswersRequest.getProjectIds(), questions);
        List<Answer> previousAnswers = answerRepository.findAllBy(answerIds);
        return PreviousAnswersWithQuestion.listOf(questions, previousAnswers);
    }

    @Transactional
    public void createBy(List<CreateAnswerDetail> answerDetails, Map<Long, Question> questionsMap,
                         Map<Long, Project> projectsMap, Archive archive, Account account) {
        List<Answer> answers = answerDetails.stream()
                                            .map(answerDetail -> {
                                                Project project = projectsMap.get(answerDetail.getProjectId());
                                                Question question = questionsMap.get(answerDetail.getQuestionId());
                                                String comment = answerDetail.getComment();
                                                return Answer.of(project, question, archive, account, comment);
                                            }).collect(Collectors.toList());
        answerRepository.saveAll(answers);
    }
}
