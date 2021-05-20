package com.careerzip.domain.record.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.domain.questionnaire.repository.QuestionnaireRepository;
import com.careerzip.domain.record.entity.Record;
import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.template.repository.TemplateRepository;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.careerzip.testobject.account.AccountFactory.createJpaTestMember;
import static com.careerzip.testobject.pagination.PaginationFactory.createPaginationOf;
import static com.careerzip.testobject.questionnaire.QuestionnaireFactory.createJpaTestQuestionnaireOf;
import static com.careerzip.testobject.record.RecordFactory.createJpaTestRecordOf;
import static com.careerzip.testobject.template.TemplateFactory.createJpaTestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.domain.Sort.Direction.DESC;

class RecordRepositoryTest extends BaseRepositoryTest {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    TemplateRepository templateRepository;

    Account account;
    Template template;
    Questionnaire questionnaire;

    @BeforeEach
    void setup() {
        account = accountRepository.save(createJpaTestMember());
        template = templateRepository.save(createJpaTestTemplate());
        questionnaire = questionnaireRepository.save(createJpaTestQuestionnaireOf(template));
    }

    @Test
    @DisplayName("Record를 Account 기준으로 조회하는 테스트")
    void findAllByAccountTest() {
        // given
        int page = 1;
        int size = 10;
        PageRequest pageRequest = CustomPageRequest.of(createPaginationOf(page, size, DESC.name()));

        List<Record> recordList = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            recordList.add(createJpaTestRecordOf(account, questionnaire));
        }

        // when
        recordRepository.saveAll(recordList);

        Page<Record> records = recordRepository.findAllBy(account, pageRequest);

        // then
        assertAll(
                () -> assertThat(records.getTotalPages()).isEqualTo(3),
                () -> assertThat(records.getNumber()).isEqualTo(page - 1),
                () -> assertThat(records.getTotalElements()).isEqualTo(recordList.size()),
                () -> assertThat(records.getNumberOfElements()).isEqualTo(size),
                () -> assertThat(records.hasPrevious()).isFalse(),
                () -> assertThat(records.hasNext()).isTrue(),
                () -> assertThat(records.getContent()
                                        .stream()
                                        .filter(record -> record.getAccount().getId().equals(account.getId()))
                                        .count()).isEqualTo(records.getSize())

        );
    }

    @Test
    @DisplayName("Account, Id를 기준으로 Record를 조회하는 테스트")
    void findByAccountAndIdTest() {
        // given
        Record targetRecord = recordRepository.save(createJpaTestRecordOf(account, questionnaire));

        Account anotherAccount = accountRepository.save(createJpaTestMember());
        Template anotherTemplate = templateRepository.save(createJpaTestTemplate());
        Questionnaire anotherQuestionnaire = questionnaireRepository.save(createJpaTestQuestionnaireOf(anotherTemplate));
        recordRepository.save(createJpaTestRecordOf(anotherAccount, anotherQuestionnaire));

        // when
        Record foundRecord = recordRepository.findBy(account, targetRecord.getId()).orElseThrow(RuntimeException::new);

        // then
        assertThat(foundRecord).usingRecursiveComparison().isEqualTo(targetRecord);
    }
}
