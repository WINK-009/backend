package com.wink.gongongu.auth.service;

import com.wink.gongongu.auth.dto.LoginResponse;
import com.wink.gongongu.auth.exception.AuthErrorCode;
import com.wink.gongongu.auth.jwt.TokenStatus;
import com.wink.gongongu.auth.jwt.service.JwtTokenProvider;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.entity.UserType;
import com.wink.gongongu.domain.user.exception.UserErrorCode;
import com.wink.gongongu.domain.user.repository.UserRepository;
import com.wink.gongongu.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(String loginToken){
        if(loginToken==null||loginToken.isBlank()){
            throw new BusinessException(AuthErrorCode.MISSING_LOGIN_TOKEN);
        }

        TokenStatus tokenStatus = jwtTokenProvider.validateToken(loginToken);
        if(tokenStatus!= TokenStatus.VALID){
            throw new BusinessException(AuthErrorCode.INVALID_LOGIN_TOKEN);
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(loginToken);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

        if(user.getUserType()== UserType.TMP){
            throw new BusinessException(AuthErrorCode.TEMP_USER_CANNOT_LOGIN);
        }

        return new LoginResponse(user.getId(),user.getNickname(),user.getUserType(),
            jwtTokenProvider.createAccessToken(user.getId()));

    }

}
