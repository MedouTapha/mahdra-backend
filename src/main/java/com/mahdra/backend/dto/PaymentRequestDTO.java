package com.mahdra.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

    @NotNull(message = "Le donateur est obligatoire")
    private Long donorId;

    private Long commitmentId;
    private Long classeId;

    @NotNull(message = "Le montant est obligatoire")
    private Double montant;

    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private String modePaiement;

    private String reference;
    private String remarque;
}
