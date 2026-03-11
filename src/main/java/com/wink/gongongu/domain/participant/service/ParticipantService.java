package com.wink.gongongu.domain.participant.service;

import com.wink.gongongu.domain.favorite.repository.FavoriteRepository;
import com.wink.gongongu.domain.participant.dto.JoinPostResponse;
import com.wink.gongongu.domain.participant.dto.JoinRequest;
import com.wink.gongongu.domain.participant.entity.Participant;
import com.wink.gongongu.domain.participant.repository.ParicipantRepository;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.repository.PostImageRepository;
import com.wink.gongongu.domain.post.repository.PostRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.repository.UserRepository;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParicipantRepository participantRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public JoinPostResponse joinPost(Long userId, Long postId, JoinRequest req){
        int q = req.quantity();
        Post post = postRepository.findByPostId(postId);

        int joined = participantRepository.sumJoinedQuantity(postId);
        if (joined + q > post.getMaxQuantity()) {
            throw new IllegalArgumentException("남은 수량을 초과했습니다");
        } // 초과 참여 방지


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        if (user == post.getUserId()){
            throw new IllegalArgumentException("자신이 만든 방에는 참여가 불가능합니다.");
        }

        Participant participant = participantRepository.findByUserId_IdAndPostId_PostIdAndDeletedFalse(userId, postId)
                .orElse(null);
        if (participant != null ){
            throw new IllegalArgumentException("이미 참여한 공구입니다.");
        }

        participantRepository.save(Participant.of(user, post, q));

        int newJoined = joined + q;
        int remaining = post.getMaxQuantity() - newJoined;

        return new JoinPostResponse(postId, userId, q, newJoined, remaining);
    }

    @Transactional
    public void deleteJoin(Long userId, Long postId){
        Post post = postRepository.findByPostId(postId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Participant participant = participantRepository.findByUserId_IdAndPostId_PostIdAndDeletedFalse(userId, postId)
                .orElseThrow(()-> new IllegalArgumentException("참여 정보 없음"));
        participant.setDeleted(true);
    }


    @Transactional
    public List<PostListResponse> JoinedPostList(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        List<Post> posts = postRepository.findAllByUserId_IdOrderByCreatedAtDesc(userId);

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
