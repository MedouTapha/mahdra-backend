package com.mahdra.backend.service;

import com.mahdra.backend.dto.DonorRequestDTO;
import com.mahdra.backend.dto.DonorResponseDTO;
import com.mahdra.backend.entity.Donor;
import com.mahdra.backend.exception.ResourceNotFoundException;
import com.mahdra.backend.mapper.DonorMapper;
import com.mahdra.backend.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DonorService {

    private final DonorRepository donorRepository;
    private final DonorMapper donorMapper;

    public Page<DonorResponseDTO> getAllDonors(Pageable pageable) {
        return donorRepository.findAll(pageable)
                .map(donorMapper::toResponseDTO);
    }

    public List<DonorResponseDTO> getAllDonors() {
        return donorRepository.findAll().stream()
                .map(donorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DonorResponseDTO getDonorById(Long id) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donateur", "id", id));
        return donorMapper.toResponseDTO(donor);
    }

    @Transactional
    public DonorResponseDTO createDonor(DonorRequestDTO requestDTO) {
        Donor donor = donorMapper.toEntity(requestDTO);
        Donor savedDonor = donorRepository.save(donor);
        return donorMapper.toResponseDTO(savedDonor);
    }

    @Transactional
    public DonorResponseDTO updateDonor(Long id, DonorRequestDTO requestDTO) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donateur", "id", id));

        donorMapper.updateEntityFromDTO(requestDTO, donor);
        Donor updatedDonor = donorRepository.save(donor);
        return donorMapper.toResponseDTO(updatedDonor);
    }

    @Transactional
    public void deleteDonor(Long id) {
        if (!donorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Donateur", "id", id);
        }
        donorRepository.deleteById(id);
    }

    public List<DonorResponseDTO> getActiveDonors() {
        return donorRepository.findByActif(true).stream()
                .map(donorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DonorResponseDTO> getDonorsByType(String type) {
        return donorRepository.findByType(type).stream()
                .map(donorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
