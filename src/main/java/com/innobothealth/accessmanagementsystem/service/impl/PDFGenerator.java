package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.MedicineEntity;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.itextpdf.layout.properties.TextAlignment.CENTER;

@Component
@Slf4j
public class PDFGenerator {

    public static byte[] generatePDF(String title, List<MedicineEntity> medicineList) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        // Add header
        addHeader(document);

        // Add title
        addTitle(document, title);

        // Add data table
        addDataTable(document, medicineList);

        // Add JFreeChart image
        addChart(document, medicineList);

        // Add footer
        addFooter(document);

        document.close();

        return baos.toByteArray();
    }

    private static void addHeader(Document document) throws IOException {
        Image logo = new Image(ImageDataFactory.create("src/main/resources/img/logo.jpg"));
        logo.setWidth(100);
        document.add(logo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(formatter);
        document.add(new Paragraph("Date and Time: " + dateTime));
    }

    private static void addTitle(Document document, String title) {
        Paragraph paragraph = new Paragraph(title);
        paragraph.setTextAlignment(CENTER);
        document.add(paragraph);
    }

    private static void addDataTable(Document document, List<MedicineEntity> medicineList) {
        Table table = new Table(4);
        table.addHeaderCell("Medicine Name");
        table.addHeaderCell("Expire Date");
        table.addHeaderCell("Quantity");
        table.addHeaderCell("Unit Price");

        for (MedicineEntity medicine : medicineList) {
            table.addCell(medicine.getMedicineName());
            table.addCell(medicine.getExpireDate());
            table.addCell(String.valueOf(medicine.getQuantity()));
            table.addCell(String.valueOf(medicine.getUnitPrice()));
        }

        document.add(table);
    }

    private static void addChart(Document document, List<MedicineEntity> medicineList) throws IOException {
        // Create a dataset for the chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populate dataset with supplier and quantity data
        for (MedicineEntity medicine : medicineList) {
            dataset.addValue(medicine.getQuantity(), "Quantity", medicine.getSupplier());
        }

        // Create the chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Supplier vs Quantity", // chart title
                "Supplier",             // x-axis label
                "Quantity",             // y-axis label
                dataset                // data
        );

        // Save the chart as PNG image to a temporary file
        File chartFile = File.createTempFile("chart", ".png");
        ChartUtils.saveChartAsPNG(chartFile, chart, 400, 300);

        // Read the temporary file into a ByteArrayOutputStream
        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        Files.copy(chartFile.toPath(), chartOutputStream);

        // Add the chart image to the PDF
        Image chartImage = new Image(ImageDataFactory.create(chartOutputStream.toByteArray()));
        document.add(chartImage);

        // Delete the temporary file
        chartFile.delete();
    }


    private static void addFooter(Document document) throws IOException {
        PdfFont font = PdfFontFactory.createFont();
        Paragraph paragraph = new Paragraph("Computer Generated Innboat RCM\nVerified by: Arunalu Bamunusinghe");
        paragraph.setFont(font);
        paragraph.setTextAlignment(CENTER);
        document.add(paragraph);
    }
}
