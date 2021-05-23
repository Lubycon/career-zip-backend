package com.careerzip.testobject.archiving;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letter.entity.Letter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.letter.LetterFactory.createLetter;

public class ArchivingFactory {

    // Archiving
    public static Archiving createArchivingOf(Long id) {
        return Archiving.builder()
                     .id(id)
                     .title("Record title")
                     .letter(createLetter())
                     .account(createMember())
                     .build();
    }

    public static Archiving createJpaTestArchivingOf(Account account, Letter letter) {
        return Archiving.builder()
                     .title("Record Title")
                     .account(account)
                     .letter(letter)
                     .build();
    }

    // RecordPage
    public static Page<Archiving> createRecordPage() {
        List<Archiving> archivings = Arrays.asList(createArchivingOf(1L), createArchivingOf(2L), createArchivingOf(3L));
        return new PageImpl<>(archivings);
    }
}
