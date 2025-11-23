package com.mahdra.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientReportResponseDTO {

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String periode;

    // Statistiques des Paiements
    private PaymentStats paiements;

    // Statistiques des Engagements
    private CommitmentStats engagements;

    // Résumé Financier
    private FinancialSummary resume;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentStats {
        private Integer totalPaiements;
        private Double montantTotal;
        private Double moyennePaiement;
        private List<PaymentDetail> details = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentDetail {
        private Long id;
        private LocalDate date;
        private Double montant;
        private String modePaiement;
        private String nomClasse;
        private String nomEleve;
        private String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitmentStats {
        private Integer totalEngagements;
        private Double montantTotal;
        private Integer enAttente;
        private Integer valides;
        private Integer rejetes;
        private List<CommitmentDetail> details = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitmentDetail {
        private Long id;
        private LocalDate dateEngagement;
        private Double montant;
        private String statut;
        private String nomClasse;
        private String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FinancialSummary {
        private Double totalPaiements;
        private Double totalEngagements;
        private Double solde; // totalPaiements - totalEngagements
        private String statut; // "Excédent" ou "Déficit"
    }
}
