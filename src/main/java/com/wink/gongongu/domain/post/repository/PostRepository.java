package com.wink.gongongu.domain.post.repository;

import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.entity.PostStatus;
import com.wink.gongongu.domain.post.entity.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findByTitleContainingIgnoreCase(String keyword); // 특정 문자열 포함 검색

    @Query("""
        SELECT p
        FROM Post p
        WHERE (:status IS NULL OR p.status = :status)
          AND (:type IS NULL OR p.type = :type)
          AND (:region IS NULL OR :region = '' OR p.region LIKE %:region%)
          AND (:query IS NULL OR :query = '' OR p.title LIKE %:query%)
        ORDER BY p.createdAt DESC
    """)
    Page<Post> search(
            @Param("query") String query,
            @Param("region") String region,
            @Param("type") PostType type,
            @Param("status") PostStatus status,
            Pageable pageable
    );

    Post findByPostId(Long postId);

    List<Post> findAllByUserId_IdOrderByCreatedAtDesc(@Param("userId") Long userId);
}
