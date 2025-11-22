package com.mahdra.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "commitments")
@Getter
@Setter
@NoArgsConstructor
public class Commitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    @NotNull(message = "Le donateur est obligatoire")
    @JsonBackReference
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
    @JsonManagedReference
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commitment)) return false;
        Commitment that = (Commitment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
