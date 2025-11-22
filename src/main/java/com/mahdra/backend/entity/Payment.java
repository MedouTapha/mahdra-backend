package com.mahdra.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    @NotNull(message = "Le donateur est obligatoire")
    private Donor donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commitment_id")
    private Commitment commitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private ClassEntity classe;

    @NotNull(message = "Le montant est obligatoire")
    @Column(nullable = false)
    private Double montant;

    @NotNull(message = "La date de paiement est obligatoire")
    @Column(nullable = false)
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    @Column(nullable = false)
    private String modePaiement; // "Espèces", "Chèque", "Virement", "Mobile Money"

    private String reference; // Numéro de chèque ou référence de transaction

    @Column(length = 1000)
    private String remarque;

    @PrePersist
    protected void onCreate() {
        if (datePaiement == null) {
            datePaiement = LocalDate.now();
        }
    }
}
