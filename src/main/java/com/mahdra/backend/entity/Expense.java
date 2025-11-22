package com.mahdra.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private ClassEntity classEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    @JsonBackReference
    private Branch branch;

    @NotNull(message = "Le montant est obligatoire")
    @Column(nullable = false)
    private Double montant;

    @NotNull(message = "La date est obligatoire")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Le type de dépense est obligatoire")
    @Column(nullable = false)
    private String type; // "Salaire", "Fournitures", "Infrastructure", "Autre"

    @Column(length = 1000)
    private String description;

    private String period; // Période (mois/année) pour les dépenses récurrentes

    private String beneficiaire; // Bénéficiaire de la dépense

    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDate.now();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;
        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
