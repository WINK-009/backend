package com.wink.gongongu.domain.address.mapper;

import com.wink.gongongu.domain.address.dto.UserAddressCreateRequest;
import com.wink.gongongu.domain.address.dto.UserAddressCreateResponse;
import com.wink.gongongu.domain.address.dto.UserAddressesResponse;
import com.wink.gongongu.domain.address.entity.Address;
import com.wink.gongongu.domain.user.entity.User;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AddressMapper {

    public static Address toEntity(User user, UserAddressCreateRequest request){
        return Address.builder()
                .recipient(request.recipient())
                .phoneNumber(request.phoneNumber())
                .roadAddress(request.roadAddress())
                .detailAddress(request.detailAddress())
                .zipCode(request.zipCode())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .isDefault(request.isDefault())
                .alias(request.alias())
                .memo(request.memo())
                .user(user)
                .build();
    }

    public static UserAddressCreateResponse toCreateResponse(Address address){
        return UserAddressCreateResponse.builder()
                .addressId(address.getId())
                .recipient(address.getRecipient())
                .phoneNumber(address.getPhoneNumber())
                .roadAddress(address.getRoadAddress())
                .detailAddress(address.getDetailAddress())
                .zipCode(address.getZipCode())
                .alias(address.getAlias())
                .memo(address.getMemo())
                .isDefault(address.isDefault())
                .build();
    }

    public static UserAddressesResponse toListResponse(List<Address> addresses){
        List<UserAddressesResponse.AddressItem> items = addresses.stream()
                .map(addr -> UserAddressesResponse.AddressItem.builder()
                        .addressId(addr.getId())
                        .alias(addr.getAlias())
                        .roadAddress(addr.getRoadAddress())
                        .detailAddress(addr.getDetailAddress())
                        .isDefault(addr.isDefault())
                        .build())
                .toList();

        return UserAddressesResponse.builder()
                .addresses(items)
                .build();
    }
}
