package com.wink.gongongu.domain.post.service;

import com.wink.gongongu.domain.post.dto.UploadPostRequest;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.entity.PostType;
import com.wink.gongongu.domain.post.repository.PostRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.entity.UserType;
import com.wink.gongongu.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long postRegister(Long userId, UploadPostRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음: " + userId));
        if (user.getUserType() == UserType.BUSINESS) {
            validateBusiness(request);
            Post post = Post.create(user, request, PostType.BUSINESS);
            return postRepository.save(post).getPostId();
        } else {
            validateIndividual(request);
            Post post = Post.create(user, request, PostType.INDIVIDUAL);
            return postRepository.save(post).getPostId();
        }



    }
    private void validateBusiness(UploadPostRequest req) {
        // BUSINESS: price 필수
        if (req.originalprice() == null)
            throw new IllegalArgumentException("BUSINESS는 original price(price) 필수");

        // BUSINESS: region 필요 없음 -> 아예 받지 않도록 null 강제(권장)
        if (req.region() != null && !req.region().isBlank())
            throw new IllegalArgumentException("BUSINESS는 region을 입력하지 않습니다");
    }

    private void validateIndividual(UploadPostRequest req) {
        // INDIVIDUAL: price 없음 -> 들어오면 에러(권장)
        if (req.originalprice() != null)
            throw new IllegalArgumentException("INDIVIDUAL은 original price(price)를 입력하지 않습니다");

        // INDIVIDUAL: region 필수
        if (req.region() == null || req.region().isBlank())
            throw new IllegalArgumentException("INDIVIDUAL은 region 필수");
    }


}
