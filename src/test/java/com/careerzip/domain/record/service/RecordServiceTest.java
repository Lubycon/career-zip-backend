package com.careerzip.domain.record.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.domain.record.dto.response.RecordsResponse;
import com.careerzip.domain.record.entity.Record;
import com.careerzip.domain.record.repository.RecordRepository;
import com.careerzip.domain.template.entity.Template;
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

import java.util.Optional;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.pagination.PaginationFactory.createPaginationOf;
import static com.careerzip.testobject.record.RecordFactory.createRecordPage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @InjectMocks
    RecordService recordService;

    @Mock
    RecordRepository recordRepository;

    @Mock
    AccountRepository accountRepository;

    @Test
    @DisplayName("특정 Account가 작성한 Record를 DTO로 반환하는 테스트")
    void findAllTest() {
        // given
        int firstIndex = 0;

        Pagination pagination = createPaginationOf(1, 10, "DESC");
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Page<Record> recordPage = createRecordPage();
        Questionnaire questionnaire = recordPage.getContent().get(firstIndex).getQuestionnaire();
        Template template = recordPage.getContent().get(0).getQuestionnaire().getTemplate();

        Account account = createMember();
        OAuthAccount loginAccount = OAuthAccount.from(account);

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(recordRepository.findAllBy(account, pageRequest)).thenReturn(recordPage);

        RecordsResponse response = recordService.findAll(loginAccount, pagination);

        // then
        assertAll(
                () -> assertThat(response.getRecords().size()).isEqualTo(recordPage.getSize()),
                () -> assertThat(response.getRecords()
                                         .stream()
                                         .filter(record -> record.getQuestionnaireTitle().equals(questionnaire.getTitle()))
                                         .count()).isEqualTo(recordPage.getSize()),
                () -> assertThat(response.getRecords()
                                         .stream()
                                         .filter(record -> record.getTemplateTitle().equals(template.getTitle()))
                                         .count()).isEqualTo(recordPage.getSize())
        );
    }
}
