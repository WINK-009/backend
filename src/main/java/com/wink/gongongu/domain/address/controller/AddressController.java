package com.wink.gongongu.domain.address.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.address.dto.UserAddressCreateRequest;
import com.wink.gongongu.domain.address.dto.UserAddressCreateResponse;
import com.wink.gongongu.domain.address.dto.UserAddressesResponse;
import com.wink.gongongu.domain.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddressCreateResponse createAddress(@RequestBody UserAddressCreateRequest request,
        @AuthenticationPrincipal UserPrincipal principal) {
        return addressService.createAddress(principal.userId(), request);
    }

    @GetMapping
    public UserAddressesResponse getAddresses(@AuthenticationPrincipal UserPrincipal principal) {
        return addressService.getUserAddresses(principal.userId());
    }
}
