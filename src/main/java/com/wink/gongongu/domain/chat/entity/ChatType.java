package com.wink.gongongu.domain.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatType {
    INDIVIDUAL("individual"),
    BUSINESS("business"),;

    private final String description;
}