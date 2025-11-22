package com.mahdra.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitmentResponseDTO {
    private Long id;
    private Long donorId;
    private String donorNom;
    private String donorPrenom;
    private Double montant;
    private LocalDate dateEngagement;
    private LocalDate dateEcheance;
    private String statut;
    private String description;
}
