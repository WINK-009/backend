package com.wink.gongongu.domain.chat.service;

import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class StubPostReader implements PostReader {

    @Override
    public Optional<String> findTitleByPostId(Long postId) {
        return Optional.empty();
    }
}