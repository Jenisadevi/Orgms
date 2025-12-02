package com.orgms.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.orgms.dao.ProjectDAO;
import com.orgms.model.Project;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple ReportService that generates:
 *  - an XLSX (Apache POI)
 *  - a PDF (iText7)
 *
 * Note: keep library versions in pom.xml (poi-ooxml, itext7-core).
 */
public class ReportService {

    /**
     * Generate weekly project report: returns list with two files [0]=xlsx, [1]=pdf
     */
    public static List<File> generateWeeklyProjectReport(Date weekStart, Date weekEnd) throws Exception {
        // fetch projects (ProjectDAO implementation must exist)
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.listAll();

        // 1) Generate XLSX
        File xlsxFile = File.createTempFile("weekly_project_report_", ".xlsx");
        try (Workbook wb = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(xlsxFile)) {
            Sheet sheet = wb.createSheet("Weekly Projects");
            // header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Project ID");
            header.createCell(1).setCellValue("Job Name");
            header.createCell(2).setCellValue("Total Hours");
            header.createCell(3).setCellValue("Total Cost");

            int r = 1;
            for (Project p : projects) {
                Row row = sheet.createRow(r++);
                Cell c0 = row.createCell(0); c0.setCellValue(nullSafe(p.getProjectId()));
                Cell c1 = row.createCell(1); c1.setCellValue(nullSafe(p.getJobName()));
                Cell c2 = row.createCell(2); c2.setCellValue(p.getTotalHours());
                Cell c3 = row.createCell(3); c3.setCellValue(p.getTotalCost());
            }

            // autosize columns a bit
            for (int i = 0; i <= 3; i++) sheet.autoSizeColumn(i);

            wb.write(fos);
        }

        // 2) Generate PDF
        File pdfFile = File.createTempFile("weekly_project_report_", ".pdf");
        try (PdfWriter writer = new PdfWriter(pdfFile);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document doc = new Document(pdfDoc)) {

            doc.add(new Paragraph("Weekly Project Report"));
            // create table with 4 columns
            Table table = new Table(new float[]{3, 5, 2, 2});
            table.setWidthPercent(100);

            // header
            table.addCell("Project ID");
            table.addCell("Job Name");
            table.addCell("Total Hours");
            table.addCell("Total Cost");

            // rows
            for (Project p : projects) {
                table.addCell(nullSafe(p.getProjectId()));
                table.addCell(nullSafe(p.getJobName()));
                table.addCell(String.valueOf(p.getTotalHours()));
                table.addCell(String.valueOf(p.getTotalCost()));
            }

            doc.add(table);
        }

        List<File> out = new ArrayList<>();
        out.add(xlsxFile);
        out.add(pdfFile);
        return out;
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }
}

