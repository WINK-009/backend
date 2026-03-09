package com.wink.gongongu.domain.address.entity;

import com.wink.gongongu.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String roadAddress;

    private String detailAddress;

    private String zipCode;

    private double latitude;

    private double longitude;

    private boolean isDefault;

    private String alias;

    @Column(columnDefinition="TEXT")
    private String memo;

    private String recipient;

    private String phoneNumber;

    public void changeDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}
