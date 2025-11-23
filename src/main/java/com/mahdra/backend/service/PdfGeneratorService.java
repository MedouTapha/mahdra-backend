package com.mahdra.backend.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.mahdra.backend.dto.ClientReportResponseDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Couleurs personnalisées
    private static final DeviceRgb PRIMARY_COLOR = new DeviceRgb(41, 128, 185);
    private static final DeviceRgb HEADER_BG = new DeviceRgb(52, 152, 219);
    private static final DeviceRgb LIGHT_GRAY = new DeviceRgb(236, 240, 241);

    public byte[] generateClientReport(ClientReportResponseDTO report) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Charger une police qui supporte l'arabe
        // Note: Pour un meilleur support arabe, vous devriez utiliser une police personnalisée
        // comme Arial Unicode MS ou Noto Sans Arabic
        PdfFont font = PdfFontFactory.createFont("Helvetica", PdfEncodings.IDENTITY_H);
        PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold", PdfEncodings.IDENTITY_H);

        document.setFont(font);

        // En-tête du rapport
        addHeader(document, boldFont, report);

        // Résumé financier
        addFinancialSummary(document, boldFont, font, report.getResume());

        // Statistiques des paiements
        addPaymentStats(document, boldFont, font, report.getPaiements());

        // Statistiques des engagements
        addCommitmentStats(document, boldFont, font, report.getEngagements());

        // Pied de page
        addFooter(document, font);

        document.close();
        return baos.toByteArray();
    }

    private void addHeader(Document document, PdfFont boldFont, ClientReportResponseDTO report) {
        // Titre principal
        Paragraph title = new Paragraph("تقرير الوضع المالي")
                .setFont(boldFont)
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5);
        document.add(title);

        // Sous-titre en français
        Paragraph subtitle = new Paragraph("Rapport de Situation Financière")
                .setFont(boldFont)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginBottom(20);
        document.add(subtitle);

        // Période
        String periode = String.format("الفترة: %s إلى %s",
                report.getDateDebut().format(DATE_FORMATTER),
                report.getDateFin().format(DATE_FORMATTER));

        Paragraph periodPara = new Paragraph(periode)
                .setFont(boldFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(periodPara);
    }

    private void addFinancialSummary(Document document, PdfFont boldFont, PdfFont font,
                                     ClientReportResponseDTO.FinancialSummary summary) {
        // Titre de section
        Paragraph sectionTitle = new Paragraph("الملخص المالي | Résumé Financier")
                .setFont(boldFont)
                .setFontSize(16)
                .setFontColor(PRIMARY_COLOR)
                .setMarginTop(10)
                .setMarginBottom(10);
        document.add(sectionTitle);

        // Table du résumé
        float[] columnWidths = {3, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths))
                .setWidth(UnitValue.createPercentValue(100));

        // En-tête
        table.addHeaderCell(createHeaderCell("البيان | Description", boldFont));
        table.addHeaderCell(createHeaderCell("المبلغ | Montant (MRU)", boldFont));

        // Données
        table.addCell(createDataCell("إجمالي المدفوعات | Total Paiements", font));
        table.addCell(createDataCell(formatAmount(summary.getTotalPaiements()), font));

        table.addCell(createDataCell("إجمالي الالتزامات | Total Engagements", font));
        table.addCell(createDataCell(formatAmount(summary.getTotalEngagements()), font));

        table.addCell(createDataCell("الرصيد | Solde", boldFont));
        Cell soldeCell = createDataCell(formatAmount(summary.getSolde()), boldFont);
        soldeCell.setBackgroundColor(summary.getSolde() >= 0 ?
                new DeviceRgb(39, 174, 96) : new DeviceRgb(231, 76, 60));
        soldeCell.setFontColor(ColorConstants.WHITE);
        table.addCell(soldeCell);

        document.add(table);
        document.add(new Paragraph("\n"));
    }

    private void addPaymentStats(Document document, PdfFont boldFont, PdfFont font,
                                  ClientReportResponseDTO.PaymentStats payments) {
        // Titre de section
        Paragraph sectionTitle = new Paragraph("المدفوعات | Paiements")
                .setFont(boldFont)
                .setFontSize(16)
                .setFontColor(PRIMARY_COLOR)
                .setMarginTop(10)
                .setMarginBottom(10);
        document.add(sectionTitle);

        // Statistiques
        Paragraph stats = new Paragraph(
                String.format("عدد المدفوعات: %d | إجمالي: %s MRU | المعدل: %s MRU",
                        payments.getTotalPaiements(),
                        formatAmount(payments.getMontantTotal()),
                        formatAmount(payments.getMoyennePaiement())))
                .setFont(font)
                .setFontSize(10)
                .setMarginBottom(10);
        document.add(stats);

        // Table des détails
        if (payments.getDetails() != null && !payments.getDetails().isEmpty()) {
            float[] columnWidths = {1.5f, 2, 1.5f, 1.5f, 2};
            Table table = new Table(UnitValue.createPercentArray(columnWidths))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setFontSize(9);

            // En-têtes
            table.addHeaderCell(createHeaderCell("التاريخ\nDate", boldFont));
            table.addHeaderCell(createHeaderCell("الصف\nClasse", boldFont));
            table.addHeaderCell(createHeaderCell("الطالب\nÉlève", boldFont));
            table.addHeaderCell(createHeaderCell("المبلغ\nMontant", boldFont));
            table.addHeaderCell(createHeaderCell("طريقة الدفع\nMode", boldFont));

            // Données
            for (ClientReportResponseDTO.PaymentDetail detail : payments.getDetails()) {
                table.addCell(createDataCell(detail.getDate().format(DATE_FORMATTER), font));
                table.addCell(createDataCell(detail.getNomClasse() != null ? detail.getNomClasse() : "-", font));
                table.addCell(createDataCell(detail.getNomEleve() != null ? detail.getNomEleve() : "-", font));
                table.addCell(createDataCell(formatAmount(detail.getMontant()), font));
                table.addCell(createDataCell(detail.getModePaiement() != null ? detail.getModePaiement() : "-", font));
            }

            document.add(table);
        }
        document.add(new Paragraph("\n"));
    }

    private void addCommitmentStats(Document document, PdfFont boldFont, PdfFont font,
                                     ClientReportResponseDTO.CommitmentStats commitments) {
        // Titre de section
        Paragraph sectionTitle = new Paragraph("الالتزامات | Engagements")
                .setFont(boldFont)
                .setFontSize(16)
                .setFontColor(PRIMARY_COLOR)
                .setMarginTop(10)
                .setMarginBottom(10);
        document.add(sectionTitle);

        // Statistiques
        Paragraph stats = new Paragraph(
                String.format("العدد الكلي: %d | قيد الانتظار: %d | تم التحقق: %d | مرفوض: %d",
                        commitments.getTotalEngagements(),
                        commitments.getEnAttente(),
                        commitments.getValides(),
                        commitments.getRejetes()))
                .setFont(font)
                .setFontSize(10)
                .setMarginBottom(10);
        document.add(stats);

        // Table des détails
        if (commitments.getDetails() != null && !commitments.getDetails().isEmpty()) {
            float[] columnWidths = {1.5f, 2.5f, 1.5f, 1.5f, 2};
            Table table = new Table(UnitValue.createPercentArray(columnWidths))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setFontSize(9);

            // En-têtes
            table.addHeaderCell(createHeaderCell("التاريخ\nDate", boldFont));
            table.addHeaderCell(createHeaderCell("الصف\nClasse", boldFont));
            table.addHeaderCell(createHeaderCell("المبلغ\nMontant", boldFont));
            table.addHeaderCell(createHeaderCell("الحالة\nStatut", boldFont));
            table.addHeaderCell(createHeaderCell("الوصف\nDescription", boldFont));

            // Données
            for (ClientReportResponseDTO.CommitmentDetail detail : commitments.getDetails()) {
                table.addCell(createDataCell(detail.getDateEngagement().format(DATE_FORMATTER), font));
                table.addCell(createDataCell(detail.getNomClasse() != null ? detail.getNomClasse() : "-", font));
                table.addCell(createDataCell(formatAmount(detail.getMontant()), font));

                Cell statutCell = createDataCell(detail.getStatut(), font);
                switch (detail.getStatut().toUpperCase()) {
                    case "VALIDE":
                        statutCell.setBackgroundColor(new DeviceRgb(39, 174, 96));
                        statutCell.setFontColor(ColorConstants.WHITE);
                        break;
                    case "REJETE":
                        statutCell.setBackgroundColor(new DeviceRgb(231, 76, 60));
                        statutCell.setFontColor(ColorConstants.WHITE);
                        break;
                    default:
                        statutCell.setBackgroundColor(new DeviceRgb(243, 156, 18));
                        break;
                }
                table.addCell(statutCell);

                table.addCell(createDataCell(detail.getDescription() != null ? detail.getDescription() : "-", font));
            }

            document.add(table);
        }
    }

    private void addFooter(Document document, PdfFont font) {
        Paragraph footer = new Paragraph("تم إنشاء هذا التقرير تلقائيًا | Ce rapport a été généré automatiquement")
                .setFont(font)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.GRAY)
                .setMarginTop(20);
        document.add(footer);
    }

    private Cell createHeaderCell(String content, PdfFont font) {
        return new Cell()
                .add(new Paragraph(content).setFont(font))
                .setBackgroundColor(HEADER_BG)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(8);
    }

    private Cell createDataCell(String content, PdfFont font) {
        return new Cell()
                .add(new Paragraph(content != null ? content : "-").setFont(font))
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(5);
    }

    private String formatAmount(Double amount) {
        if (amount == null) return "0.00";
        return String.format("%.2f", amount);
    }
}
