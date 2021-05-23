package com.careerzip.domain.archiving.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.letter.entity.Letter;
import com.careerzip.domain.letter.repository.LetterRepository;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.letterform.repository.LetterFormRepository;
import com.careerzip.global.error.exception.entity.RecordNotFoundException;
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
import static com.careerzip.testobject.letter.LetterFactory.createJpaTestLetterOf;
import static com.careerzip.testobject.archiving.ArchivingFactory.createJpaTestArchivingOf;
import static com.careerzip.testobject.letterform.TemplateFactory.createJpaTestLetterFormOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.domain.Sort.Direction.DESC;

class ArchivingRepositoryTest extends BaseRepositoryTest {

    @Autowired
    ArchivingRepository archivingRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LetterRepository letterRepository;

    @Autowired
    LetterFormRepository letterFormRepository;

    Account account;
    LetterForm letterForm;
    Letter letter;

    @BeforeEach
    void setup() {
        account = accountRepository.save(createJpaTestMember());
        letterForm = letterFormRepository.save(createJpaTestLetterFormOf());
        letter = letterRepository.save(createJpaTestLetterOf(letterForm));
    }

    @Test
    @DisplayName("Record를 Account 기준으로 조회하는 테스트")
    void findAllByAccountTest() {
        // given
        int page = 1;
        int size = 10;
        PageRequest pageRequest = CustomPageRequest.of(createPaginationOf(page, size, DESC.name()));

        List<Archiving> archivingList = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            archivingList.add(createJpaTestArchivingOf(account, letter));
        }

        // when
        archivingRepository.saveAll(archivingList);

        Page<Archiving> records = archivingRepository.findAllBy(account, pageRequest);

        // then
        assertAll(
                () -> assertThat(records.getTotalPages()).isEqualTo(3),
                () -> assertThat(records.getNumber()).isEqualTo(page - 1),
                () -> assertThat(records.getTotalElements()).isEqualTo(archivingList.size()),
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
        Archiving targetArchiving = archivingRepository.save(createJpaTestArchivingOf(account, letter));

        Account anotherAccount = accountRepository.save(createJpaTestMember());
        LetterForm anotherLetterForm = letterFormRepository.save(createJpaTestLetterFormOf());
        Letter anotherLetter = letterRepository.save(createJpaTestLetterOf(anotherLetterForm));
        archivingRepository.save(createJpaTestArchivingOf(anotherAccount, anotherLetter));

        // when
        Archiving foundArchiving = archivingRepository.findBy(account, targetArchiving.getId()).orElseThrow(RecordNotFoundException::new);

        // then
        assertThat(foundArchiving).usingRecursiveComparison().isEqualTo(targetArchiving);
    }
}
