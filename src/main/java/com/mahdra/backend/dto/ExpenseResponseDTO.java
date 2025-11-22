package com.mahdra.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDTO {
    private Long id;
    private Long classId;
    private String className;
    private Long branchId;
    private String branchNomfr;
    private Double montant;
    private LocalDate date;
    private String type;
    private String description;
    private String period;
    private String beneficiaire;
}
