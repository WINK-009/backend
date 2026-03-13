package com.wink.gongongu.domain.user.entity;

import com.wink.gongongu.domain.user.dto.SignUpRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String kakaoId;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String businessCode;

    private String profileImageUrl;

    private int payMoney;

    private String region;

    @Builder
    public User(String nickname, String kakaoId, UserType userType, String region) {
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.userType = userType;
        this.region = region;
    }

    public void signUp(SignUpRequest request) {
        this.nickname = request.nickname();
        this.userType = request.role();
        this.businessCode = request.businessCode();
        this.region = request.region();
    }

    public void updateProfile(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void chargePayMoney(int amount) {
        this.payMoney += amount;
    }

    public void usePayMoney(int amount) {
        this.payMoney -= amount;
    }

    public void updateRegion(String region) {
        this.region=region;
    }
}
