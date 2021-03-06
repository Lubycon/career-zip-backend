package com.careerzip.domain.account.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountRepositoryCustom {

    Optional<Account> findByOAuth(Provider provider, String oAuthId);

    List<Account> findAllBy(LocalDateTime startDateTIme, LocalDateTime endDateTime);

    List<Account> findAllNotArchivedBy(QuestionPaper questionPaper);
}
