package com.careerzip.testobject.careerarchive;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createQuestionPaper;

public class CareerArchiveFactory {

    // Archiving
    public static CareerArchive createCareerArchive() {
        return CareerArchive.builder()
                        .id(1L)
                        .title("Record title")
                        .questionPaper(createQuestionPaper())
                        .account(createMember())
                        .build();
    }

    public static CareerArchive careerArchiveOf(Long id) {
        return CareerArchive.builder()
                     .id(id)
                     .title("Record title")
                     .questionPaper(createQuestionPaper())
                     .account(createMember())
                     .build();
    }

    public static CareerArchive createJpaTestCareerArchiveOf(Account account, QuestionPaper questionPaper) {
        return CareerArchive.builder()
                     .title("Record Title")
                     .account(account)
                     .questionPaper(questionPaper)
                     .build();
    }

    // RecordPage
    public static Page<CareerArchive> createCareerArchivePageOf() {
        List<CareerArchive> careerArchives = Arrays.asList(careerArchiveOf(1L), careerArchiveOf(2L), careerArchiveOf(3L));
        return new PageImpl<>(careerArchives);
    }
}
