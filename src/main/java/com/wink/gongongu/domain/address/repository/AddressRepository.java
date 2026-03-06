package com.wink.gongongu.domain.address.repository;

import com.wink.gongongu.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
