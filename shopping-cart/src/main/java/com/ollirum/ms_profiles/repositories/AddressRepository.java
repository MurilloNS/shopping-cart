package com.ollirum.ms_profiles.repositories;

import com.ollirum.ms_profiles.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByProfileId(Long profileId);
}