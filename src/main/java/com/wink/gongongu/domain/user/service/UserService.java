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
import com.wink.gongongu.global.service.S3ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;

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
    public UserProfileUpdateResponse updateUserProfile(MultipartFile multipartFile, Long userId, UserProfileUpdateRequest request)
        throws IOException {
        User user = findById(userId);

        String nickname = null;
        if (request != null && request.nickname() != null && !request.nickname().isBlank()) {
            nickname = request.nickname();
        }

        String imageUrl = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            imageUrl = s3ImageService.uploadImage(multipartFile);
        }

        // 둘 다 안 들어온 경우
        if (nickname == null && imageUrl == null) {
            throw new BusinessException(UserErrorCode.IMAGE_AND_NICKNAME_EMPTY);
        }

        user.updateProfile(
            nickname != null ? nickname : user.getNickname(),
            imageUrl != null ? imageUrl : user.getProfileImageUrl()
        );

        return UserMapper.toUserProfileUpdateResponse(user);
    }
}
