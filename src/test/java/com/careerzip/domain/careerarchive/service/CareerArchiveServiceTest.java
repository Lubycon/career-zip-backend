package com.careerzip.domain.careerarchive.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.careerarchive.dto.response.archivingdetailresponse.ArchivingDetailResponse;
import com.careerzip.domain.careerarchive.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.careerarchive.dto.response.archivingsresponse.ArchivingsResponse;
import com.careerzip.domain.careerarchive.repository.CareerArchiveRepository;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.question.service.QuestionService;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.dto.OAuthAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.account.AccountFactory.createOAuthAccountOf;
import static com.careerzip.testobject.answer.AnswerFactory.createAnswers;
import static com.careerzip.testobject.careerarchive.CareerArchiveFactory.createCareerArchive;
import static com.careerzip.testobject.pagination.PaginationFactory.createPaginationOf;
import static com.careerzip.testobject.careerarchive.CareerArchiveFactory.createCareerArchivePageOf;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CareerArchiveServiceTest {

    @InjectMocks
    ArchivingService archivingService;

    @Mock
    CareerArchiveRepository careerArchiveRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    QuestionService questionService;

    @Test
    @DisplayName("특정 Account가 작성한 Archiving 리스트를 DTO로 반환하는 테스트")
    void findAllTest() {
        // given
        int firstIndex = 0;

        Pagination pagination = createPaginationOf(1, 10, "DESC");
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Page<CareerArchive> archivingPage = createCareerArchivePageOf();
        QuestionPaper questionPaper = archivingPage.getContent().get(firstIndex).getQuestionPaper();
        QuestionPaperForm questionPaperForm = archivingPage.getContent().get(0).getQuestionPaper().getQuestionPaperForm();

        Account account = createMember();
        OAuthAccount loginAccount = OAuthAccount.from(account);

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(careerArchiveRepository.findAllBy(account, pageRequest)).thenReturn(archivingPage);

        ArchivingsResponse response = archivingService.findAll(loginAccount, pagination);

        // then
        assertAll(
                () -> assertThat(response.getArchivings().size()).isEqualTo(archivingPage.getSize()),
                () -> assertThat(response.getArchivings()
                                         .stream()
                                         .filter(archiving -> archiving.getLetterTitle().equals(questionPaper.getTitle()))
                                         .count()).isEqualTo(archivingPage.getSize()),
                () -> assertThat(response.getArchivings()
                                         .stream()
                                         .filter(archiving -> archiving.getLetterFormTitle().equals(questionPaperForm.getTitle()))
                                         .count()).isEqualTo(archivingPage.getSize())
        );
    }

    @Test
    @DisplayName("특정 Account가 작성한 단일 Archiving을 DTO로 반환하는 테스트")
    void findByTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        List<QuestionWithAnswers> questionWithAnswers = QuestionWithAnswers.listOf(createQuestions(), createAnswers());
        CareerArchive careerArchive = createCareerArchive();
        Long archivingId = careerArchive.getId();
        QuestionPaper questionPaper = careerArchive.getQuestionPaper();
        QuestionPaperForm questionPaperForm = questionPaper.getQuestionPaperForm();

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(careerArchiveRepository.findBy(account, archivingId)).thenReturn(Optional.of(careerArchive));
        when(questionService.findWithAnswers(careerArchive)).thenReturn(questionWithAnswers);

        ArchivingDetailResponse response = archivingService.findBy(loginAccount, archivingId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(careerArchive.getId()),
                () -> assertThat(response.getLetterFormTitle()).isEqualTo(questionPaperForm.getTitle()),
                () -> assertThat(response.getLetterTitle()).isEqualTo(questionPaper.getTitle()),
                () -> assertThat(response.getCreatedDateTime()).isEqualTo(careerArchive.getCreatedDateTime()),
                () -> assertThat(response.getQuestions()).usingRecursiveComparison().isEqualTo(questionWithAnswers)
        );
    }
}
