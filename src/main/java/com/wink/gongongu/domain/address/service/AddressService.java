package com.wink.gongongu.domain.address.service;

import com.wink.gongongu.domain.address.dto.UserAddressCreateRequest;
import com.wink.gongongu.domain.address.dto.UserAddressCreateResponse;
import com.wink.gongongu.domain.address.dto.UserAddressDetailResponse;
import com.wink.gongongu.domain.address.dto.UserAddressUpdateRequest;
import com.wink.gongongu.domain.address.dto.UserAddressUpdateResponse;
import com.wink.gongongu.domain.address.dto.UserAddressesResponse;
import com.wink.gongongu.domain.address.entity.Address;
import com.wink.gongongu.domain.address.exception.AddressErrorCode;
import com.wink.gongongu.domain.address.mapper.AddressMapper;
import com.wink.gongongu.domain.address.repository.AddressRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.service.UserService;
import com.wink.gongongu.global.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    @Transactional
    public UserAddressCreateResponse createAddress(Long userId, UserAddressCreateRequest request) {
        User user = userService.findById(userId);

        if (!addressRepository.existsByUser(user) && !request.isDefault()) {
            throw new BusinessException(AddressErrorCode.FIRST_ADDRESS_MUST_BE_DEFAULT);
        }

        if (request.isDefault()) {
            addressRepository.findByUserAndIsDefaultTrue(user)
                .ifPresent(addr -> {
                    addr.changeDefault(false);
                });
            user.updateRegion(extractRegion(request.roadAddress()));
        }

        Address address = AddressMapper.toEntity(user, request);
        addressRepository.save(address);

        return AddressMapper.toCreateResponse(address);
    }

    private String extractRegion(String roadAddress) {
        if (roadAddress == null || roadAddress.isBlank()) {
            return null;
        }

        String[] parts = roadAddress.split(" ");

        if (parts.length < 2) {
            return roadAddress;
        }

        return parts[0] + " " + parts[1];
    }

    @Transactional(readOnly = true)
    public UserAddressesResponse getUserAddresses(Long userId) {
        List<Address> addresses = addressRepository.findByUser_IdOrderByIsDefaultDescIdAsc(userId);
        return AddressMapper.toListResponse(addresses);
    }

    @Transactional(readOnly = true)
    public UserAddressDetailResponse getAddressDetail(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUser_Id(addressId, userId)
            .orElseThrow(() -> new BusinessException(AddressErrorCode.ADDRESS_NOT_FOUND));

        return AddressMapper.toDetailResponse(address);
    }

    @Transactional
    public UserAddressUpdateResponse updateAddress(Long userId, Long addressId,
        UserAddressUpdateRequest request) {
        Address address = addressRepository.findByIdAndUser_Id(addressId, userId)
            .orElseThrow(() -> new BusinessException(AddressErrorCode.ADDRESS_NOT_FOUND));
        User user = userService.findById(userId);

        if (request.isDefault()) {
            addressRepository.findByUserAndIsDefaultTrue(user)
                .ifPresent(addr -> {
                    addr.changeDefault(false);
                });
            user.updateRegion(extractRegion(request.roadAddress()));
        } else if(address.isDefault()){ //기존 기본배송지 설정을 해제할 때
            throw new BusinessException(AddressErrorCode.DEFAULT_ADDRESS_LEAST_ONE);
        }

        address.updateAddress(request);

        return AddressMapper.toUpdateResponse(address);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUser_Id(addressId, userId)
            .orElseThrow(() -> new BusinessException(AddressErrorCode.ADDRESS_NOT_FOUND));

        if (address.isDefault()) {
            throw new BusinessException(AddressErrorCode.CANNOT_DELETE_DEFAULT_ADDRESS);
        }

        addressRepository.delete(address);

    }


}
