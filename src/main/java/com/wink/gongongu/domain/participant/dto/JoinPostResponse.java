package com.wink.gongongu.domain.participant.dto;

public record JoinPostResponse(
        Long postId,
        Long userId,
        Integer quantity,
        int joinedQuantitySum,
        int remaingQuantity
) {
}
