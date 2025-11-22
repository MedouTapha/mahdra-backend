package com.mahdra.backend.mapper;

import com.mahdra.backend.dto.PaymentRequestDTO;
import com.mahdra.backend.dto.PaymentResponseDTO;
import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.entity.Donor;
import com.mahdra.backend.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequestDTO dto, Donor donor, Commitment commitment, ClassEntity classe) {
        if (dto == null) {
            return null;
        }

        Payment entity = new Payment();
        entity.setDonor(donor);
        entity.setCommitment(commitment);
        entity.setClasse(classe);
        entity.setMontant(dto.getMontant());
        entity.setDatePaiement(dto.getDatePaiement());
        entity.setModePaiement(dto.getModePaiement());
        entity.setReference(dto.getReference());
        entity.setRemarque(dto.getRemarque());
        return entity;
    }

    public PaymentResponseDTO toResponseDTO(Payment entity) {
        if (entity == null) {
            return null;
        }

        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(entity.getId());
        dto.setMontant(entity.getMontant());
        dto.setDatePaiement(entity.getDatePaiement());
        dto.setModePaiement(entity.getModePaiement());
        dto.setReference(entity.getReference());
        dto.setRemarque(entity.getRemarque());

        if (entity.getDonor() != null) {
            dto.setDonorId(entity.getDonor().getId());
            dto.setDonorNom(entity.getDonor().getNom());
            dto.setDonorPrenom(entity.getDonor().getPrenom());
        }

        if (entity.getCommitment() != null) {
            dto.setCommitmentId(entity.getCommitment().getId());
        }

        if (entity.getClasse() != null) {
            dto.setClasseId(entity.getClasse().getId());
            dto.setClasseName(entity.getClasse().getName());
        }

        return dto;
    }

    public void updateEntityFromDTO(PaymentRequestDTO dto, Payment entity, Donor donor, Commitment commitment, ClassEntity classe) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setDonor(donor);
        entity.setCommitment(commitment);
        entity.setClasse(classe);
        entity.setMontant(dto.getMontant());
        entity.setDatePaiement(dto.getDatePaiement());
        entity.setModePaiement(dto.getModePaiement());
        entity.setReference(dto.getReference());
        entity.setRemarque(dto.getRemarque());
    }
}
