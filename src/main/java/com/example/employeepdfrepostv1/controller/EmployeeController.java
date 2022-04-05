package com.example.employeepdfrepostv1.controller;

import com.example.employeepdfrepostv1.entity.Employee;
import com.example.employeepdfrepostv1.service.EmployeeService;
import com.example.employeepdfrepostv1.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ReportService reportService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ReportService reportService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees(){
        return employeeService.fetchEmployees();
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws JRException, FileNotFoundException {
        return reportService.export(format);
    }
}
