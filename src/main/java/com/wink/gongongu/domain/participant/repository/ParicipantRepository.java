package com.wink.gongongu.domain.participant.repository;

import com.wink.gongongu.domain.participant.entity.Participant;
import com.wink.gongongu.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParicipantRepository extends JpaRepository<Participant, Long> {
    @Query("""
        select coalesce(sum(p.quantity), 0)
        from Participant p
        where p.postId.postId = :postId
          and p.deleted = false
    """)
    int sumJoinedQuantity(@Param("postId") Long postId);

    @Query("""
    select p.postId.postId as postId,
           coalesce(sum(p.quantity), 0) as joinedQuantitySum
    from Participant p
    where p.postId.postId in :postIds
      and p.deleted = false
    group by p.postId.postId
""")
    List<PostJoinedSumRow> sumJoinedQuantityByPostIds(@Param("postIds") List<Long> postIds);

    interface PostJoinedSumRow {
        Long getPostId();
        Integer getJoinedQuantitySum();
    }

    @Query("""
    select distinct p.postId
    from Participant p
    where p.userId.id = :userId
        and p.deleted = false 
    order by p.postId.createdAt desc 
""")
    List<Post> JoinedList(@Param("userId") Long userId);

    Optional<Participant> findByUserId_IdAndPostId_PostIdAndDeletedFalse(Long userId, Long postId);
    void deleteByPostId_PostId(Long postId);
}
