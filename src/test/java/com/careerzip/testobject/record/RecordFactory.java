package com.careerzip.testobject.record;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.domain.record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.questionnaire.QuestionnaireFactory.createQuestionnaire;

public class RecordFactory {

    // Record
    public static Record createRecordOf(Long id) {
        return Record.builder()
                     .id(id)
                     .title("Record title")
                     .questionnaire(createQuestionnaire())
                     .account(createMember())
                     .build();
    }

    public static Record createJpaTestRecordOf(Account account, Questionnaire questionnaire) {
        return Record.builder()
                     .title("Record Title")
                     .account(account)
                     .questionnaire(questionnaire)
                     .build();
    }

    // RecordPage
    public static Page<Record> createRecordPage() {
        List<Record> records = Arrays.asList(createRecordOf(1L), createRecordOf(2L), createRecordOf(3L));
        return new PageImpl<>(records);
    }
}
