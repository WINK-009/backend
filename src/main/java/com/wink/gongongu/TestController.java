package com.wink.gongongu;

import com.wink.gongongu.domain.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal User user) {
        return "환영합니다. "+ user.getNickname() + "님!";
    }
}
