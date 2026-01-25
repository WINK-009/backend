package com.wink.gongongu.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String kakaoId;

    private UserType userType;

    private String businessCode;

    private String profileImageUrl;

    private int payMoney;

    private String region;
}
