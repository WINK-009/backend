package com.wink.gongongu.domain.favorite.service;

import com.wink.gongongu.domain.favorite.entity.Favorite;
import com.wink.gongongu.domain.favorite.repository.FavoriteRepository;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.repository.PostRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 좋아요 누르기
    @Transactional
    public void addFavorite(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("유저 없음 : " + userId));

        Post post = postRepository.findByPostId(postId);

        favoriteRepository.save(Favorite.of(user, post));
    }

    //찜 취소
    @Transactional
    public void deleteFavorite(Long userId, Long postId){
        favoriteRepository.deleteByUserId_IdAndPostId_PostId(userId, postId);
    }

    //찜목록 조회
    @Transactional
    public List<PostListResponse> findFavPosts(@Param("userId") Long userId){
        return favoriteRepository.findFavPosts(userId)
                .stream()
                .map(PostListResponse::from)
                .toList();
    }
}
