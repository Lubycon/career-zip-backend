package com.careerzip.controller;

import com.careerzip.account.dto.request.AccountRequest;
import com.careerzip.account.dto.response.AccountSummary;
import com.careerzip.account.service.AccountService;
import com.careerzip.global.api.ApiResult;
import com.careerzip.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.careerzip.global.api.ApiResult.SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth")
    public ApiResult<AccountSummary> authorize(@RequestHeader HttpHeaders headers, @RequestBody AccountRequest accountRequest,
                                               HttpServletResponse response) {
        jwtTokenProvider.validateAuthorizationToken(headers.getFirst(HttpHeaders.AUTHORIZATION));
        AccountSummary account = accountService.find(accountRequest);
        response.addCookie(jwtTokenProvider.mapTokenToCookie(account));

        return SUCCESS(account);
    }
}