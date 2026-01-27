package com.wink.gongongu.auth.controller;

import com.wink.gongongu.auth.dto.TestTokenIssueResponse;
import com.wink.gongongu.auth.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/test-issue")
    @ResponseStatus(HttpStatus.CREATED)
    public TestTokenIssueResponse issueTestJwt() {
        return new TestTokenIssueResponse(jwtTokenProvider.createAccessToken(1L));
    }
}
