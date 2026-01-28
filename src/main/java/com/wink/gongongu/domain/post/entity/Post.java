package com.wink.gongongu.domain.post.entity;

import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private Integer price;

    @Column(name = "discount_price")
    private Long discountPrice;

    private LocalDate dueDate;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    private String description;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private String image;

    private String region;

    private java.time.LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private PostType type;
    public static Post create(
            String image,
            String title,
            Integer price,
            LocalDate dueDate,
            Integer maxQuantity,
            String description,
            String region,
            PostType type
    ) {
        Post p = new Post();
        p.image = image;
        p.title = title;
        p.price = price;
        p.dueDate = dueDate;
        p.maxQuantity = maxQuantity;
        p.description = description;
        p.region = region;
        p.type = type;
        p.status = PostStatus.OPEN;
        p.createdAt = LocalDateTime.now();
        return p;
    }

}
