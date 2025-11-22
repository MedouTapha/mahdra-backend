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
    private Long branchId;
    private String branchNomfr;
    private String niveau;
    private Integer studentCount;
    private String teacher;
    private String description;
    private Boolean active;
}
