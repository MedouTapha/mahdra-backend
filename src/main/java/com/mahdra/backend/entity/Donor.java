package com.mahdra.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "donors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(nullable = false)
    private String prenom;

    @Email(message = "L'email doit être valide")
    @Column(unique = true)
    private String email;

    private String telephone;

    @Column(length = 500)
    private String adresse;

    @NotBlank(message = "Le type est obligatoire")
    @Column(nullable = false)
    private String type; // "Personne physique" ou "Personne morale"

    @Column(nullable = false)
    private Boolean actif = true;

    @Column(nullable = false)
    private LocalDate dateInscription = LocalDate.now();

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commitment> commitments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (dateInscription == null) {
            dateInscription = LocalDate.now();
        }
        if (actif == null) {
            actif = true;
        }
    }
}
