package com.mahdra.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDTO {

    private Long classId;
    private Long branchId;

    @NotNull(message = "Le montant est obligatoire")
    private Double montant;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @NotNull(message = "Le type de dépense est obligatoire")
    private String type;

    private String description;
    private String period;
    private String beneficiaire;
}
