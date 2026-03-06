package com.wink.gongongu.domain.address.service;

import com.wink.gongongu.domain.address.dto.UserAddressCreateRequest;
import com.wink.gongongu.domain.address.dto.UserAddressCreateResponse;
import com.wink.gongongu.domain.address.dto.UserAddressesResponse;
import com.wink.gongongu.domain.address.entity.Address;
import com.wink.gongongu.domain.address.mapper.AddressMapper;
import com.wink.gongongu.domain.address.repository.AddressRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.service.UserService;
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

         if(request.isDefault()){
             addressRepository.findByUserAndIsDefaultTrue(user)
                 .ifPresent(addr -> {
                     addr.changeDefault(false);
                 });
         }

         Address address = AddressMapper.toEntity(user, request);
         addressRepository.save(address);

         return AddressMapper.toCreateResponse(address);
     }

     @Transactional(readOnly = true)
    public UserAddressesResponse getUserAddresses(Long userId) {
         List<Address> addresses = addressRepository.findByUser_Id(userId);
         return AddressMapper.toListResponse(addresses);
     }


}
