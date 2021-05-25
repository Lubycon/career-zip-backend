package com.careerzip.domain.archiving.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.ArchivingDetailResponse;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.letter.entity.Letter;
import com.careerzip.domain.archiving.dto.response.archivingsresponse.ArchivingsResponse;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.archiving.repository.ArchivingRepository;
import com.careerzip.domain.letterform.entity.LetterForm;
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
import static com.careerzip.testobject.archiving.ArchivingFactory.createArchiving;
import static com.careerzip.testobject.pagination.PaginationFactory.createPaginationOf;
import static com.careerzip.testobject.archiving.ArchivingFactory.createRecordPage;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArchivingServiceTest {

    @InjectMocks
    ArchivingService archivingService;

    @Mock
    ArchivingRepository archivingRepository;

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
        Page<Archiving> archivingPage = createRecordPage();
        Letter letter = archivingPage.getContent().get(firstIndex).getLetter();
        LetterForm letterForm = archivingPage.getContent().get(0).getLetter().getLetterForm();

        Account account = createMember();
        OAuthAccount loginAccount = OAuthAccount.from(account);

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(archivingRepository.findAllBy(account, pageRequest)).thenReturn(archivingPage);

        ArchivingsResponse response = archivingService.findAll(loginAccount, pagination);

        // then
        assertAll(
                () -> assertThat(response.getArchivings().size()).isEqualTo(archivingPage.getSize()),
                () -> assertThat(response.getArchivings()
                                         .stream()
                                         .filter(archiving -> archiving.getLetterTitle().equals(letter.getTitle()))
                                         .count()).isEqualTo(archivingPage.getSize()),
                () -> assertThat(response.getArchivings()
                                         .stream()
                                         .filter(archiving -> archiving.getLetterFormTitle().equals(letterForm.getTitle()))
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
        Archiving archiving = createArchiving();
        Long archivingId = archiving.getId();
        Letter letter = archiving.getLetter();
        LetterForm letterForm = letter.getLetterForm();

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(archivingRepository.findBy(account, archivingId)).thenReturn(Optional.of(archiving));
        when(questionService.findWithAnswers(archiving)).thenReturn(questionWithAnswers);

        ArchivingDetailResponse response = archivingService.findBy(loginAccount, archivingId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(archiving.getId()),
                () -> assertThat(response.getLetterFormTitle()).isEqualTo(letterForm.getTitle()),
                () -> assertThat(response.getLetterTitle()).isEqualTo(letter.getTitle()),
                () -> assertThat(response.getCreatedDateTime()).isEqualTo(archiving.getCreatedDateTime()),
                () -> assertThat(response.getQuestions()).usingRecursiveComparison().isEqualTo(questionWithAnswers)
        );
    }
}
