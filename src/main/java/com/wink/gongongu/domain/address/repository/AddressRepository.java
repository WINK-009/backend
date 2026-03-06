package com.wink.gongongu.domain.address.repository;

import com.wink.gongongu.domain.address.entity.Address;
import com.wink.gongongu.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser_IdOrderByIsDefaultDescIdAsc(Long userId);

    Optional<Address> findByUserAndIsDefaultTrue(User user);
}
