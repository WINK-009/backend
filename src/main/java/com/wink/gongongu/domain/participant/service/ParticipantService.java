package com.wink.gongongu.domain.participant.service;

import com.wink.gongongu.domain.favorite.repository.FavoriteRepository;
import com.wink.gongongu.domain.participant.dto.JoinPostResponse;
import com.wink.gongongu.domain.participant.dto.JoinRequest;
import com.wink.gongongu.domain.participant.entity.Participant;
import com.wink.gongongu.domain.participant.entity.ParticipantStatus;
import com.wink.gongongu.domain.participant.repository.ParicipantRepository;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.entity.PostStatus;
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

        if (remaining == 0 && post.getStatus() == PostStatus.OPEN) {
            post.changeStatus(PostStatus.CONFIRMING);
        }

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
    //참여 확정하기
    @Transactional
    public void confirmPurchase(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        Participant participant = participantRepository
                .findByUserId_IdAndPostId_PostIdAndDeletedFalse(userId, postId)
                .orElseThrow(() -> new IllegalArgumentException("참여자가 아닙니다."));

        if (participant.isIshost()) {
            throw new IllegalArgumentException("방장은 공동구매 확정 대상이 아닙니다.");
        }

        if (post.getStatus() != PostStatus.CONFIRMING) {
            throw new IllegalArgumentException("공동구매 확정 가능한 상태가 아닙니다.");
        }

        if (participant.getStatus() != ParticipantStatus.JOINED) {
            throw new IllegalArgumentException("이미 확정했거나 거래 완료된 상태입니다.");
        }

        participant.confirm();

        long totalParticipants = participantRepository
                .countByPostId_PostIdAndDeletedFalseAndIshostFalse(postId);

        long confirmedParticipants = participantRepository
                .countByPostId_PostIdAndDeletedFalseAndIshostFalseAndStatus(postId, ParticipantStatus.CONFIRMED);

        if (totalParticipants == confirmedParticipants) {
            post.changeStatus(PostStatus.TRADING);
        }
    }

    //거래 완료하기
    @Transactional
    public void completeTrade(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        Participant participant = participantRepository
                .findByUserId_IdAndPostId_PostIdAndDeletedFalse(userId, postId)
                .orElseThrow(() -> new IllegalArgumentException("참여자가 아닙니다."));

        if (participant.isIshost()) {
            throw new IllegalArgumentException("방장은 거래 완료 대상이 아닙니다.");
        }

        if (post.getStatus() != PostStatus.TRADING) {
            throw new IllegalArgumentException("거래 완료 가능한 상태가 아닙니다.");
        }

        if (participant.getStatus() != ParticipantStatus.CONFIRMED) {
            throw new IllegalArgumentException("공동구매 확정 후에만 거래 완료 가능합니다.");
        }

        participant.tradeComplete();

        long totalParticipants = participantRepository
                .countByPostId_PostIdAndDeletedFalseAndIshostFalse(postId);

        long tradedParticipants = participantRepository
                .countByPostId_PostIdAndDeletedFalseAndIshostFalseAndStatus(postId, ParticipantStatus.TRADED);

        if (totalParticipants == tradedParticipants) {
            post.changeStatus(PostStatus.DONE);
        }
    }
}
