package com.wink.gongongu.domain.address.dto;

import lombok.Builder;

@Builder
public record UserAddressDetailResponse(
    Long addressId,
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
