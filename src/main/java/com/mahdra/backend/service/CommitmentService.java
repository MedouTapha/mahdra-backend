package com.mahdra.backend.service;

import com.mahdra.backend.dto.CommitmentRequestDTO;
import com.mahdra.backend.dto.CommitmentResponseDTO;
import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.entity.Donor;
import com.mahdra.backend.exception.ResourceNotFoundException;
import com.mahdra.backend.mapper.CommitmentMapper;
import com.mahdra.backend.repository.CommitmentRepository;
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
public class CommitmentService {

    private final CommitmentRepository commitmentRepository;
    private final DonorRepository donorRepository;
    private final CommitmentMapper commitmentMapper;

    public Page<CommitmentResponseDTO> getAllCommitments(Pageable pageable) {
        return commitmentRepository.findAll(pageable)
                .map(commitmentMapper::toResponseDTO);
    }

    public List<CommitmentResponseDTO> getAllCommitments() {
        return commitmentRepository.findAll().stream()
                .map(commitmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CommitmentResponseDTO getCommitmentById(Long id) {
        Commitment commitment = commitmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement", "id", id));
        return commitmentMapper.toResponseDTO(commitment);
    }

    @Transactional
    public CommitmentResponseDTO createCommitment(CommitmentRequestDTO requestDTO) {
        Donor donor = donorRepository.findById(requestDTO.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException("Donateur", "id", requestDTO.getDonorId()));

        Commitment commitment = commitmentMapper.toEntity(requestDTO, donor);
        Commitment savedCommitment = commitmentRepository.save(commitment);
        return commitmentMapper.toResponseDTO(savedCommitment);
    }

    @Transactional
    public CommitmentResponseDTO updateCommitment(Long id, CommitmentRequestDTO requestDTO) {
        Commitment commitment = commitmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement", "id", id));

        Donor donor = donorRepository.findById(requestDTO.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException("Donateur", "id", requestDTO.getDonorId()));

        commitmentMapper.updateEntityFromDTO(requestDTO, commitment, donor);
        Commitment updatedCommitment = commitmentRepository.save(commitment);
        return commitmentMapper.toResponseDTO(updatedCommitment);
    }

    @Transactional
    public void deleteCommitment(Long id) {
        if (!commitmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Engagement", "id", id);
        }
        commitmentRepository.deleteById(id);
    }

    public List<CommitmentResponseDTO> getCommitmentsByDonor(Long donorId) {
        return commitmentRepository.findByDonorId(donorId).stream()
                .map(commitmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CommitmentResponseDTO> getCommitmentsByStatut(String statut) {
        return commitmentRepository.findByStatut(statut).stream()
                .map(commitmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
