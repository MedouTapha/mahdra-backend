package com.mahdra.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commitments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    @NotNull(message = "Le donateur est obligatoire")
    private Donor donor;

    @NotNull(message = "Le montant est obligatoire")
    @Column(nullable = false)
    private Double montant;

    @NotNull(message = "La date d'engagement est obligatoire")
    @Column(nullable = false)
    private LocalDate dateEngagement;

    private LocalDate dateEcheance;

    @NotNull(message = "Le statut est obligatoire")
    @Column(nullable = false)
    private String statut; // "En cours", "Payé", "En retard", "Annulé"

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "commitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (dateEngagement == null) {
            dateEngagement = LocalDate.now();
        }
        if (statut == null) {
            statut = "En cours";
        }
    }
}
