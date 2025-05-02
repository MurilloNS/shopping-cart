package com.ollirum.ms_profiles.mappers;

import com.ollirum.ms_profiles.dto.AddressRequestDto;
import com.ollirum.ms_profiles.dto.AddressResponseDto;
import com.ollirum.ms_profiles.entities.Address;
import com.ollirum.ms_profiles.entities.Profile;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address toEntity(AddressRequestDto dto, Profile profile) {
        Address address = new Address();
        address.setRoad(dto.getRoad());
        address.setNumber(dto.getNumber());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setProfile(profile);
        return address;
    }

    public AddressResponseDto toDto(Address address) {
        AddressResponseDto dto = new AddressResponseDto();
        dto.setId(address.getId());
        dto.setRoad(address.getRoad());
        dto.setNumber(address.getNumber());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        return dto;
    }
}