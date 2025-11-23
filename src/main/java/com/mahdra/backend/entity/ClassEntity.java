package com.mahdra.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
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

    @Column(name = "date_debut")
    private LocalDate dateDebut; // Date de début de l'année financière

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @NotNull(message = "La branche est obligatoire")
    @JsonBackReference
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassEntity)) return false;
        ClassEntity that = (ClassEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
