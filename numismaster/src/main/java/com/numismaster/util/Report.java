package com.numismaster.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.awt.Desktop;

import com.numismaster.model.Sale;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

public class Report {
    public static JasperPrint generateSaleNote(int id){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_numismaster", "root", "admin123");
            JasperDesign jasperDesign = JRXmlLoader.load("numismaster/reports/Note.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", id);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            return jasperPrint;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean downloadPDF(JasperPrint jasperPrint, Sale sale){
        try {
            JRPdfExporter exporter = new JRPdfExporter();
            
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            String desktopPath = System.getProperty("user.home") + File.separator +"Desktop";
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(desktopPath + "/Comprovante #" + sale.getId() + ".pdf"));
            
            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);
            
            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("numismaster");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");
            
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            
            exporter.exportReport();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void loadReport(JasperPrint jasperPrint, Sale sale) throws IOException, JRException {
        File tempFile = File.createTempFile("report", ".pdf");
        tempFile.deleteOnExit();
        JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());
        File file = new File(tempFile.getAbsolutePath());
        Desktop.getDesktop().open(file);
    }

}

