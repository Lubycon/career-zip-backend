package com.careerzip.testobject.record;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.domain.record.entity.Record;

public class RecordFactory {

    public static Record createJpaTestRecordOf(Account account, Questionnaire questionnaire) {
        return Record.builder()
                     .title("Record Title")
                     .account(account)
                     .questionnaire(questionnaire)
                     .build();
    }
}
