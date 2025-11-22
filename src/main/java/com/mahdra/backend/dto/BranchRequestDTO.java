package com.mahdra.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchRequestDTO {

    @NotBlank(message = "Le nom français est obligatoire")
    private String nomfr;

    @NotBlank(message = "Le nom arabe est obligatoire")
    private String nomar;
}
