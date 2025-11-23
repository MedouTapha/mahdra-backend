package com.mahdra.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassResponseDTO {
    private Long id;
    private String name;
    private String type;
    private Integer yearStart;
    private LocalDate createdDate;
    private LocalDate dateDebut;  // Date de début de l'année financière
    private LocalDate dateFin;    // Date de fin de l'année financière (calculée)
    private Long branchId;
    private String branchNomfr;
    private String niveau;
    private Integer studentCount;
    private String teacher;
    private String description;
    private Boolean active;
}
