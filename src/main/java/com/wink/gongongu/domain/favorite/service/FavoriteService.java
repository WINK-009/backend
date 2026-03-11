package com.wink.gongongu.domain.favorite.service;

import com.wink.gongongu.domain.favorite.entity.Favorite;
import com.wink.gongongu.domain.favorite.repository.FavoriteRepository;
import com.wink.gongongu.domain.participant.entity.Participant;
import com.wink.gongongu.domain.participant.repository.ParicipantRepository;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.repository.PostImageRepository;
import com.wink.gongongu.domain.post.repository.PostRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ParicipantRepository participantRepository;
    private final PostImageRepository postImageRepository;

    // 좋아요 누르기
    @Transactional
    public void addFavorite(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("유저 없음 : " + userId));

        Post post = postRepository.findByPostId(postId);

        if (favoriteRepository.existsByUserId_IdAndPostId_PostId(userId, postId)){
            throw new IllegalArgumentException("이미 찜 한 게시물 입니다.");
        }

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
        List<Post> posts = favoriteRepository.findFavPosts(userId);

        List<Long> postIds = posts.stream()
                .map(Post::getPostId)
                .toList();

        // 3) 참여 수량 합계 Map (필요 없으면 0으로 넣어도 되지만, 있는 게 더 좋음)
        Map<Long, Integer> sumMap = participantRepository.sumJoinedQuantityByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getPostId(),
                        r -> r.getJoinedQuantitySum() == null ? 0 : r.getJoinedQuantitySum()
                ));

        // 4) 찜 수 Map
        Map<Long, Integer> favMap = favoriteRepository.countFavByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getPostId(),
                        r -> r.getFavCount() == null ? 0 : r.getFavCount().intValue()
                ));
        Map<Long, String> mainImageMap = postImageRepository.findMainImagesByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        PostImageRepository.MainImageRow::getPostId,
                        PostImageRepository.MainImageRow::getImageUrl
                ));
        return posts.stream()
                .map(p -> PostListResponse.from(
                        p,
                        sumMap.getOrDefault(p.getPostId(), 0),
                        favMap.getOrDefault(p.getPostId(), 0),
                        mainImageMap.getOrDefault(p.getPostId(), null)
                ))
                .toList();
    }
}
