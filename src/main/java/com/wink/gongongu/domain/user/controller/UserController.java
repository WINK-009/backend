package com.wink.gongongu.domain.user.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.user.dto.SignUpRequest;
import com.wink.gongongu.domain.user.dto.SignUpResponse;
import com.wink.gongongu.domain.user.dto.UserProfileResponse;
import com.wink.gongongu.domain.user.dto.UserProfileUpdateRequest;
import com.wink.gongongu.domain.user.dto.UserProfileUpdateResponse;
import com.wink.gongongu.domain.user.service.UserService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping
    public UserProfileResponse getUserProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return userService.getUserProfile(principal.userId());
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserProfileUpdateResponse updateUserProfile(
        @RequestPart(value="image", required = false) MultipartFile multipartFile,
        @RequestPart(value = "request", required = false) UserProfileUpdateRequest request,
        @AuthenticationPrincipal UserPrincipal principal) throws IOException {
        return userService.updateUserProfile(multipartFile,principal.userId(), request);
    }

}
