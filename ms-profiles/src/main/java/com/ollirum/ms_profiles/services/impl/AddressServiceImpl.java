package com.ollirum.ms_profiles.services.impl;

import com.ollirum.ms_profiles.dto.AddressRequestDto;
import com.ollirum.ms_profiles.dto.AddressResponseDto;
import com.ollirum.ms_profiles.entities.Address;
import com.ollirum.ms_profiles.entities.Profile;
import com.ollirum.ms_profiles.exceptions.ProfileNotFoundException;
import com.ollirum.ms_profiles.mappers.AddressMapper;
import com.ollirum.ms_profiles.repositories.AddressRepository;
import com.ollirum.ms_profiles.repositories.ProfileRepository;
import com.ollirum.ms_profiles.services.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ProfileRepository profileRepository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ProfileRepository profileRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.profileRepository = profileRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    @Transactional
    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        Profile profile = profileRepository.findById(addressRequestDto.getProfileId())
                .orElseThrow(() -> new ProfileNotFoundException("Perfil não encontrado."));

        Address address = addressMapper.toEntity(addressRequestDto, profile);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toDto(savedAddress);
    }

    @Override
    public List<AddressResponseDto> getAddressesByProfileId(Long profileId) {
        List<Address> addresses = addressRepository.findByProfileId(profileId);
        return addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AddressResponseDto updateAddress(Long id, Map<String, Object> updates) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Endereço não encontrado!"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "road" -> address.setRoad((String) value);
                case "number" -> address.setNumber((String) value);
                case "city" -> address.setCity((String) value);
                case "state" -> address.setState((String) value);
            }
        });

        Address updatedAddress = addressRepository.save(address);
        return addressMapper.toDto(updatedAddress);
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Endereço não encontrado."));

        addressRepository.delete(address);
    }
}