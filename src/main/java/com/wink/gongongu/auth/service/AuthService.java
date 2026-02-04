package com.wink.gongongu.auth.service;

import com.wink.gongongu.auth.dto.LoginResponse;
import com.wink.gongongu.auth.dto.SignUpRequest;
import com.wink.gongongu.auth.dto.SignUpResponse;
import com.wink.gongongu.auth.exception.AuthErrorCode;
import com.wink.gongongu.auth.jwt.TokenStatus;
import com.wink.gongongu.auth.jwt.service.JwtTokenProvider;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.entity.UserType;
import com.wink.gongongu.domain.user.mapper.UserMapper;
import com.wink.gongongu.domain.user.service.UserService;
import com.wink.gongongu.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public LoginResponse login(String loginToken){
        Long userId = getUserIdLoginToken(loginToken);

        User user = userService.findById(userId);

        if(user.getUserType()== UserType.TMP){
            throw new BusinessException(AuthErrorCode.TMP_USER_CANNOT_LOGIN);
        }

        String accessToken = jwtTokenProvider.createAccessToken(userId);
        return UserMapper.toLoginResponse(user,accessToken);
    }

    @Transactional
    public SignUpResponse signUp(String loginToken, SignUpRequest request) {
        Long userId = getUserIdLoginToken(loginToken);
        User user = userService.signUp(userId, request);
        String accessToken = jwtTokenProvider.createAccessToken(userId);

        return UserMapper.toSignUpResponse(user, accessToken);
    }

    private Long getUserIdLoginToken(String loginToken) {
        if(loginToken ==null|| loginToken.isBlank()){
            throw new BusinessException(AuthErrorCode.MISSING_LOGIN_TOKEN);
        }

        TokenStatus tokenStatus = jwtTokenProvider.validateToken(loginToken);
        if(tokenStatus!= TokenStatus.VALID){
            throw new BusinessException(AuthErrorCode.INVALID_LOGIN_TOKEN);
        }

        return jwtTokenProvider.getUserIdFromToken(loginToken);
    }
}
