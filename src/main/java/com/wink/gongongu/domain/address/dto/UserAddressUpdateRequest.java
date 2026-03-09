package com.wink.gongongu.domain.address.dto;

public record UserAddressUpdateRequest(
    String recipient,
    String phoneNumber,
    String roadAddress,
    String detailAddress,
    String zipCode,
    String alias,
    String memo,
    boolean isDefault
) {

}
