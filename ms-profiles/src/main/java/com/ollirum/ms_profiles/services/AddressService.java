package com.ollirum.ms_profiles.services;

import com.ollirum.ms_profiles.dto.AddressRequestDto;
import com.ollirum.ms_profiles.dto.AddressResponseDto;

import java.util.List;
import java.util.Map;

public interface AddressService {
    AddressResponseDto createAddress(AddressRequestDto addressRequestDto);
    List<AddressResponseDto> getAddressesByProfileId(Long profileId);
    AddressResponseDto updateAddress(Long id, Map<String, Object> updates);
    void deleteAddress(Long id);
}