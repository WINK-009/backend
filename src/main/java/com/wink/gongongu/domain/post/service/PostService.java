package com.wink.gongongu.domain.post.service;

import com.wink.gongongu.domain.post.dto.UploadPostRequest;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long postRegister(UploadPostRequest request){
        Post post = Post.create(
                request.image(),
                request.title(),
                request.price(),
                request.duedate(),
                request.maxQuantity(),
                request.description(),
                request.region(),
                request.postType()
        );

        Post saved = postRepository.save(post);
        return saved.getPostId();



    }
}
