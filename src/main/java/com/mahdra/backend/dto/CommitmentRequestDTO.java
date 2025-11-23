package com.mahdra.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitmentRequestDTO {

    @NotNull(message = "Le donateur est obligatoire")
    private Long donorId;

    @NotNull(message = "Le montant est obligatoire")
    private Double montant;

    @NotNull(message = "La date d'engagement est obligatoire")
    private LocalDate dateEngagement;

    private LocalDate dateEcheance;

    @NotNull(message = "Le statut est obligatoire")
    private String statut;

    private String description;

    private List<Long> classeIds;
}
