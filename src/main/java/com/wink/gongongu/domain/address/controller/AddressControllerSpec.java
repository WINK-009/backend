package com.wink.gongongu.domain.address.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.address.dto.UserAddressCreateRequest;
import com.wink.gongongu.domain.address.dto.UserAddressCreateResponse;
import com.wink.gongongu.domain.address.dto.UserAddressDetailResponse;
import com.wink.gongongu.domain.address.dto.UserAddressesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "유저 주소록 관련 API")
public interface AddressControllerSpec {

    @Operation(
        summary = "주소록 등록 API",
        description = """
            alias(별칭)과 memo는 빈값일 시 null로 보내주시면 됩니다.<br>
            기본 주소록 시 isDefault에 true를 담아주시고 아니면 false를 담아주세요.<br>
            이미 기본 주소록으로 등록된 주소록이 존재시 그 주소록의 기본 주소록 설정이 해제됩니다.
            """
    )
    UserAddressCreateResponse createAddress(UserAddressCreateRequest request, UserPrincipal principal);

    @Operation(
        summary = "주소록 목록 조회 API"
    )
    UserAddressesResponse getAddresses(UserPrincipal principal);

    @Operation(
        summary = "주소록 상세 조회 API"
    )
    UserAddressDetailResponse getAddressDetail(UserPrincipal principal, Long addressId);


}
