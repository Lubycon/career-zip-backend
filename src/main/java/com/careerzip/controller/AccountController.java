package com.careerzip.controller;

import com.careerzip.domain.account.dto.request.AccountUpdateRequest;
import com.careerzip.domain.account.dto.response.AccountArchiveExist;
import com.careerzip.domain.account.dto.response.AccountDetail;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.service.AccountService;
import com.careerzip.global.api.ApiResponse;
import com.careerzip.security.oauth.annotation.LoginAccount;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth")
    public ApiResponse<AccountDetail> authorize(@RequestHeader HttpHeaders headers, HttpServletResponse response) {
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String jwtToken = accountService.issueJwtToken(authorizationHeader);
        AccountDetail account = accountService.findBy(authorizationHeader);
        response.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
        return ApiResponse.success(account);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ApiResponse<AccountSummary> update(@LoginAccount OAuthAccount loginAccount, @PathVariable Long id,
                                              @Valid @RequestBody AccountUpdateRequest accountUpdateRequest) {
        AccountSummary account = accountService.update(loginAccount, id, accountUpdateRequest);
        return ApiResponse.success(account);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/post/exist")
    public ApiResponse<AccountArchiveExist> hasPostedArchive(@LoginAccount OAuthAccount loginAccount) {
        AccountArchiveExist accountArchiveExist = accountService.hasPostedArchiveThisWeek(loginAccount);
        return ApiResponse.success(accountArchiveExist);
    }
}
