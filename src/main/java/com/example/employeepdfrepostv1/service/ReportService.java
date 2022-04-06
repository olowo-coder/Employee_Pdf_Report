package com.example.employeepdfrepostv1.service;

import com.example.employeepdfrepostv1.entity.Employee;
import com.example.employeepdfrepostv1.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public ReportService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String export(String format) throws FileNotFoundException, JRException {
        String path = "/Users/mac/Desktop/reports/";
        List<Employee> employees = employeeRepository.findAll();
        File file = ResourceUtils.getFile("classpath:employees.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
        Map<String, Object> map = new HashMap<>();
        map.put("createdBy", "Xen coporate, Olowo");
        JasperPrint print = JasperFillManager.fillReport(jasperReport, map, dataSource);

        if(format.equalsIgnoreCase("html")){
            JasperExportManager
                    .exportReportToHtmlFile(print, path + "employee.html");
        }
        if(format.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(print, path + "employee.pdf");
        }

        return "Report Generated";
    }

    public byte[] downloadReport(String title) throws FileNotFoundException, JRException {
        String path = "/Users/mac/Desktop/reports/";
        JRBeanCollectionDataSource beanCollectionDataSource =
                new JRBeanCollectionDataSource(employeeRepository.findAll());
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/employees.jrxml"));
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//        JasperExportManager.exportReportToPdfFile(report, path + "employee.pdf");
        byte[] data = JasperExportManager.exportReportToPdf(report);
        return data;
    }
}
