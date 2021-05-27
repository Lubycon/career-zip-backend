package com.careerzip.domain.archive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.PaperRepository;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.questionpaperform.repository.QuestionPaperFormRepository;
import com.careerzip.global.error.exception.entity.ArchiveNotFoundException;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createJpaTestMember;
import static com.careerzip.testobject.pagination.PaginationFactory.createPaginationOf;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createJpaTestQuestionPaperOf;
import static com.careerzip.testobject.archive.ArchiveFactory.createJpaTestArchiveOf;
import static com.careerzip.testobject.questionpaperform.QuestionPaperFormFactory.createJpaTestQuestionPaperForm;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.domain.Sort.Direction.DESC;

class ArchiveRepositoryTest extends BaseRepositoryTest {

    @Autowired
    ArchiveRepository archiveRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PaperRepository paperRepository;

    @Autowired
    QuestionPaperFormRepository questionPaperFormRepository;

    Account account;
    QuestionPaperForm questionPaperForm;
    QuestionPaper questionPaper;

    @BeforeEach
    void setup() {
        account = accountRepository.save(createJpaTestMember());
        questionPaperForm = questionPaperFormRepository.save(createJpaTestQuestionPaperForm());
        questionPaper = paperRepository.save(createJpaTestQuestionPaperOf(questionPaperForm));
    }

    @Test
    @DisplayName("Archiving 목록을 Account 기준으로 조회하는 테스트")
    void findAllByAccountTest() {
        // given
        int page = 1;
        int size = 10;
        PageRequest pageRequest = CustomPageRequest.of(createPaginationOf(page, size, DESC.name()));

        List<Archive> archiveList = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            archiveList.add(createJpaTestArchiveOf(account, questionPaper));
        }

        // when
        archiveRepository.saveAll(archiveList);

        Page<Archive> records = archiveRepository.findAllBy(account, pageRequest);

        // then
        assertAll(
                () -> assertThat(records.getTotalPages()).isEqualTo(3),
                () -> assertThat(records.getNumber()).isEqualTo(page - 1),
                () -> assertThat(records.getTotalElements()).isEqualTo(archiveList.size()),
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
    @DisplayName("Account, Id를 기준으로 Archiving을 조회하는 테스트")
    void findByAccountAndIdTest() {
        // given
        Archive targetArchive = archiveRepository.save(createJpaTestArchiveOf(account, questionPaper));

        Account anotherAccount = accountRepository.save(createJpaTestMember());
        QuestionPaperForm anotherQuestionPaperForm = questionPaperFormRepository.save(createJpaTestQuestionPaperForm());
        QuestionPaper anotherQuestionPaper = paperRepository.save(createJpaTestQuestionPaperOf(anotherQuestionPaperForm));
        archiveRepository.save(createJpaTestArchiveOf(anotherAccount, anotherQuestionPaper));

        // when
        Archive foundArchive = archiveRepository.findBy(account, targetArchive.getId()).orElseThrow(ArchiveNotFoundException::new);

        // then
        assertThat(foundArchive).usingRecursiveComparison().isEqualTo(targetArchive);
    }
}
