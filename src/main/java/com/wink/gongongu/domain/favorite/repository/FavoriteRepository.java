package com.wink.gongongu.domain.favorite.repository;

import com.wink.gongongu.domain.favorite.entity.Favorite;
import com.wink.gongongu.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    void deleteByUserId_IdAndPostId_PostId(Long userId, Long postId);

    @Query("""
        select f.postId
        from Favorite f
        where f.userId.id = :userId
        order by f.favId desc
    """)
    List<Post> findFavPosts(@Param("userId") Long userId);

    void deleteByPostId_PostId(Long postId);
}
