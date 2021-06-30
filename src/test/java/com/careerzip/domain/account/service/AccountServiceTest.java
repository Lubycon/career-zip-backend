package com.careerzip.domain.account.service;

import com.careerzip.domain.account.dto.request.AccountUpdateRequest;
import com.careerzip.domain.account.dto.request.UtmSourceRequest;
import com.careerzip.domain.account.dto.response.AccountArchiveExist;
import com.careerzip.domain.account.dto.response.AccountDetail;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.acquisition.entity.Acquisition;
import com.careerzip.domain.acquisition.repository.AcquisitionRepository;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.global.error.exception.BusinessException;
import com.careerzip.global.error.exception.business.AccountMismatchException;
import com.careerzip.global.error.response.ErrorCode;
import com.careerzip.global.jwt.JwtTokenProvider;
import com.careerzip.security.oauth.dto.OAuthAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.careerzip.testobject.account.AccountFactory.*;
import static com.careerzip.testobject.archive.ArchiveFactory.createArchive;
import static com.careerzip.testobject.jwt.JwtFactory.createValidJwtTokenOf;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createQuestionPaper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    ArchiveRepository archiveRepository;

    @Mock
    QuestionPaperRepository questionPaperRepository;

    @Mock
    AcquisitionRepository acquisitionRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("임시 토큰으로 Account 데이터를 찾아서 AccountDetail DTO로 반환하는 테스트")
    void findByTest() {
        // given
        Account account = createMember();
        String preAuthToken = createValidJwtTokenOf(account);

        // when
        when(jwtTokenProvider.parsePreAuthToken(preAuthToken)).thenReturn(account.getId());
        when(accountRepository.findById((account.getId()))).thenReturn(Optional.ofNullable(account));

        AccountDetail accountDetail = accountService.findBy(preAuthToken);

        // then
        assertAll(
                () -> assertThat(accountDetail.getId()).isEqualTo(account.getId()),
                () -> assertThat(accountDetail.getName()).isEqualTo(account.getName()),
                () -> assertThat(accountDetail.getEmail()).isEqualTo(account.getEmail()),
                () -> assertThat(accountDetail.getAvatarUrl()).isEqualTo(account.getAvatarUrl()),
                () -> assertThat(accountDetail.getJob()).isEqualTo(account.getJob().getName()),
                () -> assertThat(accountDetail.getUtmSource()).isEqualTo(account.getAcquisition().getUtmSource())
        );
    }

    @Test
    @DisplayName("성공 - 회원 정보 수정 메서드 테스트")
    void updateTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        Long accountId = account.getId();

        AccountUpdateRequest updateRequest = createAccountUpdateRequest("newName", "newEmail");

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        AccountSummary updatedAccount = accountService.update(loginAccount, accountId, updateRequest);

        // then
        assertThat(updatedAccount.getName()).isEqualTo(updateRequest.getName());
        assertThat(updatedAccount.getEmail()).isEqualTo(updateRequest.getEmail());
    }

    @Test
    @DisplayName("에러 - 일치하지 않는 Account로 회원 정보 수정 요청을 했을 때 예외가 발생하는 테스트")
    void update() {
        // given
        ErrorCode errorCode = ErrorCode.ACCOUNT_MISMATCH_EXCEPTION;
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        Long differentId = account.getId() + 1;
        AccountUpdateRequest updateRequest = createAccountUpdateRequest("newName", "newEmail");

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));

        // then
        assertThatThrownBy(() -> accountService.update(loginAccount, differentId, updateRequest))
                .isExactlyInstanceOf(AccountMismatchException.class)
                .isInstanceOf(BusinessException.class)
                .hasMessage(errorCode.getMessage());
    }

    @Test
    @DisplayName("Archive를 이미 등록 했을 때 id 값과 함께 반환 되는 테스트")
    void hasPostedArchiveThisWeekTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        QuestionPaper questionPaper = createQuestionPaper();
        Archive archive = createArchive();

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(questionPaperRepository.findLatest()).thenReturn(Optional.of(questionPaper));
        when(archiveRepository.findBy(account, questionPaper)).thenReturn(Optional.of(archive));

        AccountArchiveExist accountArchiveExist = accountService.hasPostedArchiveThisWeek(loginAccount);

        // then
        assertThat(accountArchiveExist.isArchived()).isTrue();
        assertThat(accountArchiveExist.getId()).isEqualTo(archive.getId());
    }

    @Test
    @DisplayName("Archive가 아직 등록되어 있지 않을 때 id 값 없이 반환 되는 테스트")
    void hasPostedArchiveThisWeekWhenHasNotArchivedTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        QuestionPaper questionPaper = createQuestionPaper();

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(questionPaperRepository.findLatest()).thenReturn(Optional.of(questionPaper));
        when(archiveRepository.findBy(account, questionPaper)).thenReturn(Optional.empty());

        AccountArchiveExist accountArchiveExist = accountService.hasPostedArchiveThisWeek(loginAccount);

        // then
        assertThat(accountArchiveExist.isArchived()).isFalse();
        assertThat(accountArchiveExist.getId()).isNull();
    }

    @Test
    @DisplayName("Account에 UtmSource를 추가하는 메서드 테스트")
    void addUtmSourceTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);

        String utmSource = "utmSource";
        UtmSourceRequest request = new UtmSourceRequest();
        request.setUtmSource(utmSource);
        Acquisition acquisition = Acquisition.from(utmSource);

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(acquisitionRepository.findByUtmSource(utmSource)).thenReturn(Optional.of(acquisition));

        Account updatedAccount = accountService.addUtmSource(loginAccount, request);

        // then
        assertThat(updatedAccount.getAcquisition()).usingRecursiveComparison().isEqualTo(acquisition);
    }
}
