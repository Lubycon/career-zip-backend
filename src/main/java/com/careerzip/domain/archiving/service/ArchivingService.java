package com.careerzip.domain.archiving.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letterformquestion.service.LetterFormQuestionService;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.ArchivingDetailResponse;
import com.careerzip.domain.archiving.dto.response.archivingsresponse.ArchivingSummary;
import com.careerzip.domain.archiving.dto.response.archivingsresponse.ArchivingsResponse;
import com.careerzip.domain.archiving.repository.ArchivingRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.error.exception.entity.RecordNotFoundException;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArchivingService {

    private final ArchivingRepository archivingRepository;
    private final AccountRepository accountRepository;
    private final LetterFormQuestionService letterFormQuestionService;


    public ArchivingsResponse findAll(OAuthAccount loginAccount, Pagination pagination) {
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow(AccountNotFoundException::new);
        Page<Archiving> recordPage = archivingRepository.findAllBy(account, pageRequest);
        List<ArchivingSummary> records = ArchivingSummary.listOf(recordPage);
        return ArchivingsResponse.of(recordPage, records);
    }

    public ArchivingDetailResponse findBy(OAuthAccount loginAccount, Long recordId) {
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow();
        Archiving archiving = archivingRepository.findBy(account, recordId).orElseThrow(RecordNotFoundException::new);

        return null;
    }
}
