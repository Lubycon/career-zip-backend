package com.careerzip.domain.answer.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.project.repository.ProjectRepository;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.questionitem.repository.QuestionItemRepository;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.questionpaperform.repository.QuestionPaperFormRepository;
import com.careerzip.domain.questiontype.entity.QuestionType;
import com.careerzip.domain.questiontype.repository.QuestionTypeRepository;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.QuestionPaperFormFactory.createJpaQuestionPaperForm;
import static com.careerzip.testobject.account.AccountFactory.createJpaMember;
import static com.careerzip.testobject.answer.AnswerFactory.createJpaAnswerOf;
import static com.careerzip.testobject.archive.ArchiveFactory.createJpaArchiveOf;
import static com.careerzip.testobject.project.ProjectFactory.createJpaProjectOf;
import static com.careerzip.testobject.question.QuestionFactory.createJpaQuestionOf;
import static com.careerzip.testobject.questionitem.QuestionItemFactory.createJpaTextQuestionItemOf;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createJpaQuestionPaperOf;
import static com.careerzip.testobject.questiontype.QuestionTypeFactory.createJpaQuestionType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AnswerRepositoryTest extends BaseRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    QuestionPaperRepository questionPaperRepository;

    @Autowired
    QuestionPaperFormRepository questionPaperFormRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionItemRepository questionItemRepository;

    @Autowired
    QuestionTypeRepository questionTypeRepository;

    @Autowired
    ArchiveRepository archiveRepository;

    @Test
    @DisplayName("가장 최근 기록한 Answer ID 목록을 조회하는 메서드 테스트")
    void findAllIdsByTest() {
        // given
        Account account = accountRepository.save(createJpaMember());
        QuestionPaperForm questionPaperForm = questionPaperFormRepository.save(createJpaQuestionPaperForm());
        QuestionType questionType = questionTypeRepository.save(createJpaQuestionType());
        QuestionItem questionItem = questionItemRepository.save(createJpaTextQuestionItemOf(questionType));

        Question firstQuestion = questionRepository.save(createJpaQuestionOf(questionPaperForm, questionItem));
        Question secondQuestion = questionRepository.save(createJpaQuestionOf(questionPaperForm, questionItem));
        List<Long> questionIds = Arrays.asList(firstQuestion.getId(), secondQuestion.getId());

        Project firstProject = projectRepository.save(createJpaProjectOf(account));
        Project secondProject = projectRepository.save(createJpaProjectOf(account));
        List<Long> projectIds = Arrays.asList(firstProject.getId(), secondProject.getId());

        QuestionPaper questionPaper = questionPaperRepository.save(createJpaQuestionPaperOf(questionPaperForm));
        Archive archive = archiveRepository.save(createJpaArchiveOf(account, questionPaper));

        Answer beforeFirstProjectFirstQuestion = answerRepository.save(createJpaAnswerOf(firstProject, firstQuestion, archive, account));
        Answer beforeSecondProjectFirstQuestion = answerRepository.save(createJpaAnswerOf(secondProject, firstQuestion, archive, account));
        Answer afterFirstProjectFirstQuestion = answerRepository.save(createJpaAnswerOf(firstProject, firstQuestion, archive, account));
        Answer afterSecondProjectFirstQuestion = answerRepository.save(createJpaAnswerOf(secondProject, firstQuestion, archive, account));
        Answer beforeFirstProjectSecondQuestion = answerRepository.save(createJpaAnswerOf(firstProject, secondQuestion, archive, account));
        Answer afterFirstProjectSecondQuestion = answerRepository.save(createJpaAnswerOf(firstProject, secondQuestion, archive, account));
        Answer latestFirstProjectSecondQuestion = answerRepository.save(createJpaAnswerOf(firstProject, secondQuestion, archive, account));
        answerRepository.saveAll(Arrays.asList(beforeFirstProjectFirstQuestion, beforeSecondProjectFirstQuestion,
                                               afterFirstProjectFirstQuestion, afterSecondProjectFirstQuestion,
                                               beforeFirstProjectSecondQuestion, afterFirstProjectSecondQuestion,
                                               latestFirstProjectSecondQuestion));

        // when
        List<Long> latestIds = answerRepository.findAllPreviousIdsBy(account.getId(), projectIds, questionIds);

        // then
        assertAll(
                () -> assertThat(latestIds.size()).isEqualTo(3),
                () -> assertThat(latestIds).contains(afterFirstProjectFirstQuestion.getId()),
                () -> assertThat(latestIds).contains(afterSecondProjectFirstQuestion.getId()),
                () -> assertThat(latestIds).contains(latestFirstProjectSecondQuestion.getId())
        );

    }

}
