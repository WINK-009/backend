package com.wink.gongongu.domain.address.repository;

import com.wink.gongongu.domain.address.entity.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser_Id(Long userId);
}
