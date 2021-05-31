package com.careerzip.domain.archive.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateAnswerDetail;
import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateArchiveRequest;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.project.repository.ProjectRepository;
import com.careerzip.domain.project.service.ProjectService;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.question.service.QuestionService;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.questionitem.repository.QuestionItemRepository;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.questionpaperform.repository.QuestionPaperFormRepository;
import com.careerzip.domain.questiontype.entity.QuestionType;
import com.careerzip.domain.questiontype.repository.QuestionTypeRepository;
import com.careerzip.global.error.exception.entity.ArchiveNotFoundException;
import com.careerzip.security.oauth.dto.OAuthAccount;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import com.careerzip.testconfig.base.BaseServiceJpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.careerzip.testobject.QuestionPaperFormFactory.createJpaQuestionPaperForm;
import static com.careerzip.testobject.account.AccountFactory.createJpaMember;
import static com.careerzip.testobject.account.AccountFactory.createOAuthAccountOf;
import static com.careerzip.testobject.answer.AnswerFactory.createCreateAnswerDetailsOf;
import static com.careerzip.testobject.archive.ArchiveFactory.createCreateArchiveRequestOf;
import static com.careerzip.testobject.project.ProjectFactory.createJpaProjectOf;
import static com.careerzip.testobject.question.QuestionFactory.createJpaQuestionOf;
import static com.careerzip.testobject.questionitem.QuestionItemFactory.createJpaTextQuestionItemOf;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createJpaQuestionPaperOf;
import static com.careerzip.testobject.questiontype.QuestionTypeFactory.createJpaQuestionType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ArchiveServiceJpaTest extends BaseServiceJpaTest {

    @Autowired
    ArchiveService archiveService;

    @Autowired
    AnswerService answerService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ProjectService projectService;

    @Test
    @DisplayName("Project, Question, Archive, Account로 Answer를 생성하는 테스트")
    void createAnswersByTest() {
        // given
        QuestionPaperForm questionPaperForm = questionPaperFormRepository.save(createJpaQuestionPaperForm());
        QuestionPaper questionPaper = questionPaperRepository.save(createJpaQuestionPaperOf(questionPaperForm));
        QuestionType questionType = questionTypeRepository.save(createJpaQuestionType());
        QuestionItem questionItem = questionItemRepository.save(createJpaTextQuestionItemOf(questionType));
        List<Question> questions = questionRepository.saveAll(List.of(createJpaQuestionOf(questionPaperForm, questionItem),
                                                                      createJpaQuestionOf(questionPaperForm, questionItem),
                                                                      createJpaQuestionOf(questionPaperForm, questionItem)));

        Account account = accountRepository.save(createJpaMember());
        Project project = projectRepository.save(createJpaProjectOf(account));

        OAuthAccount loginAccount = createOAuthAccountOf(account);

        List<CreateAnswerDetail> answerDetails = createCreateAnswerDetailsOf(questions, project);
        CreateArchiveRequest request = createCreateArchiveRequestOf(questionPaper.getId(), answerDetails);

        // when
        Long createdArchiveId = archiveService.createBy(loginAccount, request);
        Archive archive = archiveRepository.findById(createdArchiveId).orElseThrow(ArchiveNotFoundException::new);

        // then
        assertAll(
                () -> assertThat(archiveRepository.count()).isEqualTo(1),
                () -> assertThat(archive.getQuestionPaper()).isEqualTo(questionPaper),
                () -> assertThat(accountRepository.findById(account.getId())).isEqualTo(Optional.of(account)),
                () -> assertThat(answerRepository.count()).isEqualTo(answerDetails.size()),
                () -> assertThat(projectRepository.count()).isEqualTo(1)
        );
    }
}
