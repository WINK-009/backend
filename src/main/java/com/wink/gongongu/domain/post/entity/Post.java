package com.wink.gongongu.domain.post.entity;

import com.wink.gongongu.domain.post.dto.UploadPostRequest;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.entity.UserType;
import com.wink.gongongu.global.common.BaseTimeEntity;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    private String title;

    private Integer price;

    @Column(name = "discount_price")
    private Integer originalprice;

    private LocalDate dueDate;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    private String description;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private String image;

    private String region;


    @Enumerated(EnumType.STRING)
    private PostType type;

    private Integer likeCount;


    public static Post create(User user, UploadPostRequest req, PostType type) {
        Post p = new Post();
        p.userId = user;
        p.title = req.title();
        p.region = req.region();
        p.description = req.description();
        p.price = req.price();
        p.originalprice = req.originalprice();
        p.maxQuantity = req.maxQuantity();
        p.dueDate = req.duedate();

        // 글 타입을 userType에서 자동 결정
        p.type = (user.getUserType() == UserType.BUSINESS) ? PostType.BUSINESS : PostType.INDIVIDUAL;
        p.status = PostStatus.OPEN;
        p.likeCount = 0;
        return p;
    }
    public void updateImage(String imageUrl) {
        this.image = imageUrl;
    }

    public void changeStatus(PostStatus status) {
        this.status = status;
    }

}
