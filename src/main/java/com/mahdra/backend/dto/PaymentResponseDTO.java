package com.mahdra.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private Long donorId;
    private String donorNom;
    private String donorPrenom;
    private Long commitmentId;
    private Long classeId;
    private String classeName;
    private Double montant;
    private LocalDate datePaiement;
    private String modePaiement;
    private String reference;
    private String remarque;
}
