package com.careerzip.domain.archiving.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.question.service.QuestionService;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.ArchivingDetailResponse;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archiving.dto.response.archivingsresponse.ArchivingSummary;
import com.careerzip.domain.archiving.dto.response.archivingsresponse.ArchivingsResponse;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.archiving.repository.ArchivingRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.error.exception.entity.ArchivingNotFoundException;
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
    private final QuestionService questionService;

    public ArchivingsResponse findAll(OAuthAccount loginAccount, Pagination pagination) {
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow(AccountNotFoundException::new);
        Page<Archiving> archivingPage = archivingRepository.findAllBy(account, pageRequest);
        List<ArchivingSummary> archivings = ArchivingSummary.listOf(archivingPage);
        return ArchivingsResponse.of(archivingPage, archivings);
    }

    public ArchivingDetailResponse findBy(OAuthAccount loginAccount, Long archivingId) {
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow();
        Archiving archiving = archivingRepository.findBy(account, archivingId).orElseThrow(ArchivingNotFoundException::new);
        List<QuestionWithAnswers> questionWithAnswers = questionService.findWithAnswers(archiving);
        return ArchivingDetailResponse.of(archiving, questionWithAnswers);
    }
}
