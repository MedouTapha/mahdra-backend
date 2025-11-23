package com.mahdra.backend.mapper;

import com.mahdra.backend.dto.CommitmentRequestDTO;
import com.mahdra.backend.dto.CommitmentResponseDTO;
import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.entity.Donor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommitmentMapper {

    public Commitment toEntity(CommitmentRequestDTO dto, Donor donor, List<ClassEntity> classes) {
        if (dto == null) {
            return null;
        }

        Commitment entity = new Commitment();
        entity.setDonor(donor);
        entity.setMontant(dto.getMontant());
        entity.setDateEngagement(dto.getDateEngagement());
        entity.setDateEcheance(dto.getDateEcheance());
        entity.setStatut(dto.getStatut());
        entity.setDescription(dto.getDescription());

        if (classes != null) {
            entity.setClasses(classes);
        }

        return entity;
    }

    public CommitmentResponseDTO toResponseDTO(Commitment entity) {
        if (entity == null) {
            return null;
        }

        CommitmentResponseDTO dto = new CommitmentResponseDTO();
        dto.setId(entity.getId());
        dto.setMontant(entity.getMontant());
        dto.setDateEngagement(entity.getDateEngagement());
        dto.setDateEcheance(entity.getDateEcheance());
        dto.setStatut(entity.getStatut());
        dto.setDescription(entity.getDescription());

        if (entity.getDonor() != null) {
            dto.setDonorId(entity.getDonor().getId());
            dto.setDonorNom(entity.getDonor().getNom());
            dto.setDonorPrenom(entity.getDonor().getPrenom());
        }

        if (entity.getClasses() != null) {
            List<CommitmentResponseDTO.ClassSimpleDTO> classDTOs = entity.getClasses().stream()
                .map(classEntity -> new CommitmentResponseDTO.ClassSimpleDTO(
                    classEntity.getId(),
                    classEntity.getName(),
                    classEntity.getType()
                ))
                .collect(Collectors.toList());
            dto.setClasses(classDTOs);
        }

        return dto;
    }

    public void updateEntityFromDTO(CommitmentRequestDTO dto, Commitment entity, Donor donor, List<ClassEntity> classes) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setDonor(donor);
        entity.setMontant(dto.getMontant());
        entity.setDateEngagement(dto.getDateEngagement());
        entity.setDateEcheance(dto.getDateEcheance());
        entity.setStatut(dto.getStatut());
        entity.setDescription(dto.getDescription());

        if (classes != null) {
            entity.getClasses().clear();
            entity.getClasses().addAll(classes);
        } else {
            entity.getClasses().clear();
        }
    }
}
