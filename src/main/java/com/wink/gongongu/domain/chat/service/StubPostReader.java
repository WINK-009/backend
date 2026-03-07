package com.wink.gongongu.domain.chat.service;

import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(PostReader.class)
public class StubPostReader implements PostReader {

    @Override
    public Optional<String> findTitleByPostId(Long postId) {
        return Optional.empty();
    }
}