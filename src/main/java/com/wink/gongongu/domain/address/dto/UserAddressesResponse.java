package com.wink.gongongu.domain.address.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record UserAddressesResponse(
    List<AddressItem> addresses
) {
    @Builder
    public record AddressItem(
        Long addressId,
        String alias,
        String roadAddress,
        String detailAddress,
        boolean isDefault
    ) {
    }

}
