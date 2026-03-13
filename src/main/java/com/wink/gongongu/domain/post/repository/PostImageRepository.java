package com.wink.gongongu.domain.post.repository;

import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    Optional<PostImage> findByPostId_PostIdAndIsMainTrue(Long postId);

    List<PostImage> findAllByPostId_PostIdOrderByImageIdAsc(Long postId);

    @Query("""
        select pi.postId.postId as postId, pi.imageUrl as imageUrl
        from PostImage pi
        where pi.postId.postId in :postIds
          and pi.isMain = true
    """)
    List<MainImageRow> findMainImagesByPostIds(@Param("postIds") List<Long> postIds);



    interface MainImageRow {
        String getImageUrl();
        Long getPostId();
    }



    void deleteByPostId_PostId(Long postId);


}
