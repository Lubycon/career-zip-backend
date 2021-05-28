package com.careerzip.testobject.archive;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createQuestionPaper;

public class ArchiveFactory {

    // Archiving
    public static Archive createArchive() {
        return Archive.builder()
                        .id(1L)
                        .title("Record title")
                        .questionPaper(createQuestionPaper())
                        .account(createMember())
                        .build();
    }

    public static Archive createArchiveOf(Long id) {
        return Archive.builder()
                     .id(id)
                     .title("Record title")
                     .questionPaper(createQuestionPaper())
                     .account(createMember())
                     .build();
    }

    public static Archive createJpaTestArchiveOf(Account account, QuestionPaper questionPaper) {
        return Archive.builder()
                     .title("Record Title")
                     .account(account)
                     .questionPaper(questionPaper)
                     .build();
    }

    // RecordPage
    public static Page<Archive> createArchivePageOf() {
        List<Archive> archives = Arrays.asList(createArchiveOf(1L), createArchiveOf(2L), createArchiveOf(3L));
        return new PageImpl<>(archives);
    }
}
