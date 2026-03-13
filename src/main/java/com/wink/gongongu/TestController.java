package com.wink.gongongu;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal UserPrincipal principal) {
        return "환영합니다. "+ userService.findById(principal.userId()).getNickname() + "님!";
    }
}
