package com.wink.gongongu.domain.participant.entity;

import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name ="participants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantsId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post postId;

    @Column(nullable = false)
    private Integer quantity;

    private boolean ishost;


    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public static Participant of(User user, Post post, Integer quantity) {
        Participant p = new Participant();
        p.userId = user;
        p.postId = post;
        p.quantity = quantity;
        p.deleted = false;
        return p;
    }
}
