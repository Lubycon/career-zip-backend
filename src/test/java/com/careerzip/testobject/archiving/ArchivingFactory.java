package com.careerzip.testobject.archiving;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createQuestionPaper;

public class ArchivingFactory {

    // Archiving
    public static Archiving createArchiving() {
        return Archiving.builder()
                        .id(1L)
                        .title("Record title")
                        .questionPaper(createQuestionPaper())
                        .account(createMember())
                        .build();
    }

    public static Archiving createArchivingOf(Long id) {
        return Archiving.builder()
                     .id(id)
                     .title("Record title")
                     .questionPaper(createQuestionPaper())
                     .account(createMember())
                     .build();
    }

    public static Archiving createJpaTestArchivingOf(Account account, QuestionPaper questionPaper) {
        return Archiving.builder()
                     .title("Record Title")
                     .account(account)
                     .questionPaper(questionPaper)
                     .build();
    }

    // RecordPage
    public static Page<Archiving> createRecordPage() {
        List<Archiving> archivings = Arrays.asList(createArchivingOf(1L), createArchivingOf(2L), createArchivingOf(3L));
        return new PageImpl<>(archivings);
    }
}
