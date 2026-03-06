package com.wink.gongongu.domain.address.dto;

import java.util.List;

public record UserAddressesResponse(
    List<AddressItem> addresses
) {
    public record AddressItem(
        Long addressId,
        String alias,
        String roadAddress,
        String detailAddress,
        boolean isDefault
    ) {
    }

}
