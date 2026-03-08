package com.wink.gongongu.domain.participant.service;

import com.wink.gongongu.domain.participant.dto.JoinPostResponse;
import com.wink.gongongu.domain.participant.dto.JoinRequest;
import com.wink.gongongu.domain.participant.entity.Participant;
import com.wink.gongongu.domain.participant.repository.ParicipantRepository;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.repository.PostRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParicipantRepository paricipantRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public JoinPostResponse joinPost(Long userId, Long postId, JoinRequest req){
        int q = req.quantity();
        Post post = postRepository.findByPostId(postId);

        int joined = paricipantRepository.sumJoinedQuantity(postId);
        if (joined + q > post.getMaxQuantity()) {
            throw new IllegalArgumentException("남은 수량을 초과했습니다");
        } // 초과 참여 방지





        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        paricipantRepository.save(Participant.of(user, post, q));

        int newJoined = joined + q;
        int remaining = post.getMaxQuantity() - newJoined;

        return new JoinPostResponse(postId, userId, q, newJoined, remaining);
    }

}
