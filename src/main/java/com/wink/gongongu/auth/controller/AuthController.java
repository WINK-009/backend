package com.wink.gongongu.auth.controller;

import com.wink.gongongu.auth.dto.LoginResponse;
import com.wink.gongongu.auth.dto.SignUpRequest;
import com.wink.gongongu.auth.dto.SignUpResponse;
import com.wink.gongongu.auth.dto.TestTokenIssueResponse;
import com.wink.gongongu.auth.jwt.service.JwtTokenProvider;
import com.wink.gongongu.auth.service.AuthService;
import com.wink.gongongu.auth.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @PostMapping("/test-issue")
    @ResponseStatus(HttpStatus.CREATED)
    public TestTokenIssueResponse issueTestJwt() {
        return new TestTokenIssueResponse(jwtTokenProvider.createAccessToken(1L));
    }

    @GetMapping("/login")
    public LoginResponse login(
        @CookieValue(value = "LOGIN_TOKEN",required = false) String loginToken
    ){
        return authService.login(loginToken);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signup(
        @CookieValue(value = "LOGIN_TOKEN",required = false) String loginToken,
        @RequestBody SignUpRequest request,
        HttpServletResponse response
    ){
        CookieUtil.deleteLoginTokenCookie(response);
        return authService.signUp(loginToken, request);
    }
}
