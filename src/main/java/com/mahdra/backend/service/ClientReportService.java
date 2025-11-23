package com.mahdra.backend.service;

import com.mahdra.backend.dto.ClientReportRequestDTO;
import com.mahdra.backend.dto.ClientReportResponseDTO;
import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.entity.Donor;
import com.mahdra.backend.entity.Payment;
import com.mahdra.backend.exception.ResourceNotFoundException;
import com.mahdra.backend.repository.CommitmentRepository;
import com.mahdra.backend.repository.DonorRepository;
import com.mahdra.backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientReportService {

    private final PaymentRepository paymentRepository;
    private final CommitmentRepository commitmentRepository;
    private final DonorRepository donorRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ClientReportResponseDTO generateReport(Long donorId, ClientReportRequestDTO request) {
        // Vérifier que le donateur existe
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Donateur non trouvé avec l'ID: " + donorId));

        ClientReportResponseDTO report = new ClientReportResponseDTO();
        report.setDateDebut(request.getDateDebut());
        report.setDateFin(request.getDateFin());
        report.setPeriode(String.format("Du %s au %s",
                request.getDateDebut().format(DATE_FORMATTER),
                request.getDateFin().format(DATE_FORMATTER)));

        // Récupérer les paiements
        List<Payment> payments = getPayments(donorId, request);
        ClientReportResponseDTO.PaymentStats paymentStats = buildPaymentStats(payments);
        report.setPaiements(paymentStats);

        // Récupérer les engagements
        List<Commitment> commitments = getCommitments(donorId, request);
        ClientReportResponseDTO.CommitmentStats commitmentStats = buildCommitmentStats(commitments);
        report.setEngagements(commitmentStats);

        // Calculer le résumé financier
        ClientReportResponseDTO.FinancialSummary summary = buildFinancialSummary(
                paymentStats.getMontantTotal(),
                commitmentStats.getMontantTotal()
        );
        report.setResume(summary);

        return report;
    }

    private List<Payment> getPayments(Long donorId, ClientReportRequestDTO request) {
        List<Payment> payments = paymentRepository.findByDonorIdAndDatePaiementBetween(
                donorId,
                request.getDateDebut(),
                request.getDateFin()
        );

        // Filtrer par branche si spécifié
        if (request.getBranchId() != null) {
            payments = payments.stream()
                    .filter(p -> p.getClasse() != null &&
                            p.getClasse().getBranch() != null &&
                            p.getClasse().getBranch().getId().equals(request.getBranchId()))
                    .collect(Collectors.toList());
        }

        // Filtrer par classe si spécifié
        if (request.getClassId() != null) {
            payments = payments.stream()
                    .filter(p -> p.getClasse() != null &&
                            p.getClasse().getId().equals(request.getClassId()))
                    .collect(Collectors.toList());
        }

        return payments;
    }

    private List<Commitment> getCommitments(Long donorId, ClientReportRequestDTO request) {
        List<Commitment> commitments = commitmentRepository.findByDonorIdAndDateEngagementBetween(
                donorId,
                request.getDateDebut(),
                request.getDateFin()
        );

        // Filtrer par branche ou classe si nécessaire
        if (request.getBranchId() != null || request.getClassId() != null) {
            commitments = commitments.stream()
                    .filter(c -> matchesFilters(c, request.getBranchId(), request.getClassId()))
                    .collect(Collectors.toList());
        }

        return commitments;
    }

    private boolean matchesFilters(Commitment commitment, Long branchId, Long classId) {
        if (commitment.getClasses() == null || commitment.getClasses().isEmpty()) {
            return true; // Engagement général
        }

        return commitment.getClasses().stream().anyMatch(classe -> {
            boolean branchMatch = branchId == null ||
                    (classe.getBranch() != null && classe.getBranch().getId().equals(branchId));
            boolean classMatch = classId == null || classe.getId().equals(classId);
            return branchMatch && classMatch;
        });
    }

    private ClientReportResponseDTO.PaymentStats buildPaymentStats(List<Payment> payments) {
        ClientReportResponseDTO.PaymentStats stats = new ClientReportResponseDTO.PaymentStats();

        double totalAmount = payments.stream()
                .mapToDouble(Payment::getMontant)
                .sum();

        stats.setTotalPaiements(payments.size());
        stats.setMontantTotal(totalAmount);
        stats.setMoyennePaiement(payments.isEmpty() ? 0.0 : totalAmount / payments.size());

        List<ClientReportResponseDTO.PaymentDetail> details = payments.stream()
                .map(this::toPaymentDetail)
                .collect(Collectors.toList());
        stats.setDetails(details);

        return stats;
    }

    private ClientReportResponseDTO.CommitmentStats buildCommitmentStats(List<Commitment> commitments) {
        ClientReportResponseDTO.CommitmentStats stats = new ClientReportResponseDTO.CommitmentStats();

        double totalAmount = commitments.stream()
                .mapToDouble(Commitment::getMontant)
                .sum();

        long enAttente = commitments.stream()
                .filter(c -> "En cours".equalsIgnoreCase(c.getStatut()) ||
                        "EN_ATTENTE".equalsIgnoreCase(c.getStatut()))
                .count();

        long valides = commitments.stream()
                .filter(c -> "Payé".equalsIgnoreCase(c.getStatut()) ||
                        "VALIDE".equalsIgnoreCase(c.getStatut()))
                .count();

        long rejetes = commitments.stream()
                .filter(c -> "Annulé".equalsIgnoreCase(c.getStatut()) ||
                        "REJETE".equalsIgnoreCase(c.getStatut()) ||
                        "En retard".equalsIgnoreCase(c.getStatut()))
                .count();

        stats.setTotalEngagements(commitments.size());
        stats.setMontantTotal(totalAmount);
        stats.setEnAttente((int) enAttente);
        stats.setValides((int) valides);
        stats.setRejetes((int) rejetes);

        List<ClientReportResponseDTO.CommitmentDetail> details = commitments.stream()
                .map(this::toCommitmentDetail)
                .collect(Collectors.toList());
        stats.setDetails(details);

        return stats;
    }

    private ClientReportResponseDTO.FinancialSummary buildFinancialSummary(Double totalPayments,
                                                                            Double totalCommitments) {
        ClientReportResponseDTO.FinancialSummary summary = new ClientReportResponseDTO.FinancialSummary();
        summary.setTotalPaiements(totalPayments);
        summary.setTotalEngagements(totalCommitments);

        double solde = totalPayments - totalCommitments;
        summary.setSolde(solde);
        summary.setStatut(solde >= 0 ? "Excédent" : "Déficit");

        return summary;
    }

    private ClientReportResponseDTO.PaymentDetail toPaymentDetail(Payment payment) {
        ClientReportResponseDTO.PaymentDetail detail = new ClientReportResponseDTO.PaymentDetail();
        detail.setId(payment.getId());
        detail.setDate(payment.getDatePaiement());
        detail.setMontant(payment.getMontant());
        detail.setModePaiement(payment.getModePaiement());
        detail.setDescription(payment.getDescription());

        if (payment.getClasse() != null) {
            detail.setNomClasse(payment.getClasse().getName());
        }

        return detail;
    }

    private ClientReportResponseDTO.CommitmentDetail toCommitmentDetail(Commitment commitment) {
        ClientReportResponseDTO.CommitmentDetail detail = new ClientReportResponseDTO.CommitmentDetail();
        detail.setId(commitment.getId());
        detail.setDateEngagement(commitment.getDateEngagement());
        detail.setMontant(commitment.getMontant());
        detail.setStatut(commitment.getStatut());
        detail.setDescription(commitment.getDescription());

        // Prendre le nom de la première classe si disponible
        if (commitment.getClasses() != null && !commitment.getClasses().isEmpty()) {
            detail.setNomClasse(commitment.getClasses().get(0).getName());
        }

        return detail;
    }
}
