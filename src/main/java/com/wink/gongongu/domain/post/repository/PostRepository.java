package com.wink.gongongu.domain.post.repository;

import com.wink.gongongu.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
