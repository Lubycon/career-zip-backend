package com.careerzip.controller;

import com.careerzip.domain.account.dto.request.AccountUpdateRequest;
import com.careerzip.domain.account.service.AccountService;
import com.careerzip.security.oauth.annotation.LoginAccount;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/auth")
    public void authorize(@RequestHeader HttpHeaders headers, HttpServletResponse response) {
        String jwtToken = accountService.issueJwtToken(headers.getFirst(HttpHeaders.AUTHORIZATION));
        response.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{accountId}")
    public void update(@LoginAccount OAuthAccount loginAccount, @PathVariable Long accountId,
                       @RequestBody AccountUpdateRequest accountUpdateRequest) {
        accountService.update(loginAccount, accountId, accountUpdateRequest);
    }
}
