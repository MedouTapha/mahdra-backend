package com.mahdra.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la classe est obligatoire")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Le type est obligatoire")
    @Column(nullable = false)
    private String type; // "Coranique" ou "Franco-arabe"

    @NotNull(message = "L'année de début est obligatoire")
    @Column(nullable = false)
    private Integer yearStart;

    @Column(nullable = false)
    private LocalDate createdDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @NotNull(message = "La branche est obligatoire")
    private Branch branch;

    private String niveau; // Optional: niveau de la classe

    private Integer studentCount; // Optional: nombre d'élèves

    private String teacher; // Optional: nom de l'enseignant

    @Column(length = 500)
    private String description; // Optional: description de la classe

    @Column(nullable = false)
    private Boolean active = true; // La classe est-elle active?

    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDate.now();
        }
        if (active == null) {
            active = true;
        }
    }
}
