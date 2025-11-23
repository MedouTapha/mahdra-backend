package com.mahdra.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequestDTO {

    @NotBlank(message = "Le nom de la classe est obligatoire")
    private String name;

    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotNull(message = "L'année de début est obligatoire")
    private Integer yearStart;

    private LocalDate dateDebut; // Date de début de l'année financière (optionnel, si null utilisera createdDate)

    @NotNull(message = "La branche est obligatoire")
    private Long branchId;

    private String niveau;
    private Integer studentCount;
    private String teacher;
    private String description;
    private Boolean active;
}
