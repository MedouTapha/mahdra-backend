package com.mahdra.backend.mapper;

import com.mahdra.backend.dto.CommitmentRequestDTO;
import com.mahdra.backend.dto.CommitmentResponseDTO;
import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.entity.Donor;
import org.springframework.stereotype.Component;

@Component
public class CommitmentMapper {

    public Commitment toEntity(CommitmentRequestDTO dto, Donor donor) {
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

        return dto;
    }

    public void updateEntityFromDTO(CommitmentRequestDTO dto, Commitment entity, Donor donor) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setDonor(donor);
        entity.setMontant(dto.getMontant());
        entity.setDateEngagement(dto.getDateEngagement());
        entity.setDateEcheance(dto.getDateEcheance());
        entity.setStatut(dto.getStatut());
        entity.setDescription(dto.getDescription());
    }
}
