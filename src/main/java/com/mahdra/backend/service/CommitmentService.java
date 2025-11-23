package com.mahdra.backend.service;

import com.mahdra.backend.dto.CommitmentRequestDTO;
import com.mahdra.backend.dto.CommitmentResponseDTO;
import com.mahdra.backend.dto.CommitmentStatsDTO;
import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.entity.Donor;
import com.mahdra.backend.exception.ResourceNotFoundException;
import com.mahdra.backend.mapper.CommitmentMapper;
import com.mahdra.backend.repository.ClassRepository;
import com.mahdra.backend.repository.CommitmentRepository;
import com.mahdra.backend.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommitmentService {

    private final CommitmentRepository commitmentRepository;
    private final DonorRepository donorRepository;
    private final ClassRepository classRepository;
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

        List<ClassEntity> classes = getClassesFromIds(requestDTO.getClasseIds());

        Commitment commitment = commitmentMapper.toEntity(requestDTO, donor, classes);
        Commitment savedCommitment = commitmentRepository.save(commitment);
        return commitmentMapper.toResponseDTO(savedCommitment);
    }

    @Transactional
    public CommitmentResponseDTO updateCommitment(Long id, CommitmentRequestDTO requestDTO) {
        Commitment commitment = commitmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement", "id", id));

        Donor donor = donorRepository.findById(requestDTO.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException("Donateur", "id", requestDTO.getDonorId()));

        List<ClassEntity> classes = getClassesFromIds(requestDTO.getClasseIds());

        commitmentMapper.updateEntityFromDTO(requestDTO, commitment, donor, classes);
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

    public CommitmentStatsDTO getCommitmentStatsByYear(Integer year) {
        List<Object[]> statsByStatus = commitmentRepository.statsByYear(year);

        CommitmentStatsDTO stats = new CommitmentStatsDTO();
        stats.setYear(year);

        double totalAmount = 0;
        int totalCount = 0;
        Map<String, CommitmentStatsDTO.StatutStats> byStatut = new HashMap<>();

        for (Object[] row : statsByStatus) {
            String statut = (String) row[0];
            Long count = (Long) row[1];
            Double amount = (Double) row[2];

            totalCount += count;
            totalAmount += amount != null ? amount : 0.0;

            byStatut.put(statut, new CommitmentStatsDTO.StatutStats(count, amount));
        }

        stats.setTotalCount(totalCount);
        stats.setTotalAmount(totalAmount);
        stats.setByStatut(byStatut);

        return stats;
    }

    private List<ClassEntity> getClassesFromIds(List<Long> classeIds) {
        if (classeIds == null || classeIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<ClassEntity> classes = classRepository.findAllById(classeIds);

        // Vérifier que toutes les classes ont été trouvées
        if (classes.size() != classeIds.size()) {
            List<Long> foundIds = classes.stream()
                    .map(ClassEntity::getId)
                    .collect(Collectors.toList());
            List<Long> missingIds = classeIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toList());

            throw new ResourceNotFoundException("Classe(s) non trouvée(s) avec les IDs: " + missingIds);
        }

        return classes;
    }
}
