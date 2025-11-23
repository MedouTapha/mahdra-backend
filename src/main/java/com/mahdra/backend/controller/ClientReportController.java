package com.mahdra.backend.controller;

import com.mahdra.backend.dto.ClientReportRequestDTO;
import com.mahdra.backend.dto.ClientReportResponseDTO;
import com.mahdra.backend.service.ClientReportService;
import com.mahdra.backend.service.PdfGeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClientReportController {

    private final ClientReportService reportService;
    private final PdfGeneratorService pdfGeneratorService;

    /**
     * Générer un rapport JSON des paiements et engagements d'un client
     */
    @GetMapping("/clients/{donorId}")
    public ResponseEntity<ClientReportResponseDTO> getClientReport(
            @PathVariable Long donorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) Long classId) {

        ClientReportRequestDTO request = new ClientReportRequestDTO(dateDebut, dateFin, branchId, classId);
        ClientReportResponseDTO report = reportService.generateReport(donorId, request);

        return ResponseEntity.ok(report);
    }

    /**
     * Générer un rapport PDF des paiements et engagements d'un client
     * Supporte l'arabe
     */
    @GetMapping("/clients/{donorId}/pdf")
    public ResponseEntity<byte[]> getClientReportPdf(
            @PathVariable Long donorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) Long classId) {

        try {
            // Générer le rapport
            ClientReportRequestDTO request = new ClientReportRequestDTO(dateDebut, dateFin, branchId, classId);
            ClientReportResponseDTO report = reportService.generateReport(donorId, request);

            // Générer le PDF
            byte[] pdfBytes = pdfGeneratorService.generateClientReport(report);

            // Préparer la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline",
                    String.format("rapport_client_%d_%s_%s.pdf",
                            donorId,
                            dateDebut.toString(),
                            dateFin.toString()));
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Générer un rapport PDF via POST (pour des requêtes plus complexes)
     */
    @PostMapping("/clients/{donorId}/pdf")
    public ResponseEntity<byte[]> generateClientReportPdf(
            @PathVariable Long donorId,
            @Valid @RequestBody ClientReportRequestDTO request) {

        try {
            // Générer le rapport
            ClientReportResponseDTO report = reportService.generateReport(donorId, request);

            // Générer le PDF
            byte[] pdfBytes = pdfGeneratorService.generateClientReport(report);

            // Préparer la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment",
                    String.format("rapport_client_%d.pdf", donorId));
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
