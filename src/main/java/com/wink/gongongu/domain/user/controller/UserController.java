package com.wink.gongongu.domain.user.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.user.dto.SignUpRequest;
import com.wink.gongongu.domain.user.dto.SignUpResponse;
import com.wink.gongongu.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerSpec {

    private final UserService userService;

    @Override
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse singUp(@RequestBody SignUpRequest request,
        @AuthenticationPrincipal UserPrincipal principal) {
        return userService.signUp(principal.userId(), request);
    }

}
