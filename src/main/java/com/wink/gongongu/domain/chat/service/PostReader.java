package com.wink.gongongu.domain.chat.service;

import java.util.Optional;

public interface PostReader {
    Optional<String> findTitleByPostId(Long postId);

    default Optional<Long> findAuthorUserIdByPostId(Long postId) {
        return Optional.empty();
    }
}
