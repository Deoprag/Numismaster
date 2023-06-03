package com.numismaster.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Report {
    public JasperPrint generateSaleNote(int id){
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
}
