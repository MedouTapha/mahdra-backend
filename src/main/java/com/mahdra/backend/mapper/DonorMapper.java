package com.mahdra.backend.mapper;

import com.mahdra.backend.dto.DonorRequestDTO;
import com.mahdra.backend.dto.DonorResponseDTO;
import com.mahdra.backend.entity.Donor;
import org.springframework.stereotype.Component;

@Component
public class DonorMapper {

    public Donor toEntity(DonorRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Donor entity = new Donor();
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setAdresse(dto.getAdresse());
        entity.setType(dto.getType());
        entity.setActif(dto.getActif() != null ? dto.getActif() : true);
        return entity;
    }

    public DonorResponseDTO toResponseDTO(Donor entity) {
        if (entity == null) {
            return null;
        }

        DonorResponseDTO dto = new DonorResponseDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setEmail(entity.getEmail());
        dto.setTelephone(entity.getTelephone());
        dto.setAdresse(entity.getAdresse());
        dto.setType(entity.getType());
        dto.setActif(entity.getActif());
        dto.setDateInscription(entity.getDateInscription());
        return dto;
    }

    public void updateEntityFromDTO(DonorRequestDTO dto, Donor entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setAdresse(dto.getAdresse());
        entity.setType(dto.getType());
        if (dto.getActif() != null) {
            entity.setActif(dto.getActif());
        }
    }
}
