package com.wink.gongongu.domain.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Entity
@Getter
@Table(name="post_imgae")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post postId;

    private String imageUrl;

    private boolean isMain;

    public static PostImage of(Post post, String imageUrl, boolean isMain){
        PostImage pi = new PostImage();
        pi.postId = post;
        pi.imageUrl = imageUrl;
        pi.isMain = isMain;
        return pi;
    }
    public void setMain(boolean main) {
        this.isMain = main;
    }
}
