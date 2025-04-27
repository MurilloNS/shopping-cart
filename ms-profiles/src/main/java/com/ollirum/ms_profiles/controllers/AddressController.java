package com.ollirum.ms_profiles.controllers;

import com.ollirum.ms_profiles.dto.AddressRequestDto;
import com.ollirum.ms_profiles.dto.AddressResponseDto;
import com.ollirum.ms_profiles.services.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto addressRequestDto) {
        AddressResponseDto addressResponse = addressService.createAddress(addressRequestDto);
        return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<List<AddressResponseDto>> getAddressesByProfile(@PathVariable Long profileId) {
        List<AddressResponseDto> addresses = addressService.getAddressesByProfileId(profileId);
        return ResponseEntity.ok(addresses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        AddressResponseDto updatedAddress = addressService.updateAddress(id, updates);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}