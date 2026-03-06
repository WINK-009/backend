package com.wink.gongongu.domain.user.service;

import com.wink.gongongu.domain.user.dto.SignUpRequest;
import com.wink.gongongu.domain.user.dto.SignUpResponse;
import com.wink.gongongu.domain.user.dto.UserProfileResponse;
import com.wink.gongongu.domain.user.dto.UserProfileUpdateRequest;
import com.wink.gongongu.domain.user.dto.UserProfileUpdateResponse;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.entity.UserType;
import com.wink.gongongu.domain.user.exception.UserErrorCode;
import com.wink.gongongu.domain.user.mapper.UserMapper;
import com.wink.gongongu.domain.user.repository.UserRepository;
import com.wink.gongongu.global.exception.BusinessException;
import jakarta.transaction.TransactionScoped;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public SignUpResponse signUp(Long userId, SignUpRequest request) {
        if(request.role()!= UserType.INDIVIDUAL && request.role()!= UserType.BUSINESS){
            throw new BusinessException(UserErrorCode.INVALID_USER_TYPE);
        }
        User user = findById(userId);
        if(user.getUserType()!= UserType.TMP){
            throw new BusinessException(UserErrorCode.USER_ALREADY_SIGNED_UP);
        }
        user.signUp(request);
        return UserMapper.toSignUpResponse(user);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) {
        User user = findById(userId);
        return UserMapper.toUserProfileResponse(user);
    }

    @Transactional
    public UserProfileUpdateResponse updateUserProfile(Long userId, UserProfileUpdateRequest request) {
        User user = findById(userId);
        user.updateNickname(request.nickname());

        return UserMapper.toUserProfileUpdateResponse(user);
    }
}
