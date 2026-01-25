package com.wink.gongongu.auth.controller;

import com.wink.gongongu.auth.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String issueTestJwt() {
        return jwtTokenProvider.createAccessToken(1L);
    }
}
