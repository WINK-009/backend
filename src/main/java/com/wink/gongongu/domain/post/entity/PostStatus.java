package com.wink.gongongu.domain.post.entity;

public enum PostStatus {
    OPEN,
    CONFIRMING, //공구 확정 단계
    TRADING,// 거래 완료 전 (거래 진행 단계)
    DONE
}
