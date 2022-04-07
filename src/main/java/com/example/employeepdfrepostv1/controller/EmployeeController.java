package com.example.employeepdfrepostv1.controller;

import com.example.employeepdfrepostv1.entity.Employee;
import com.example.employeepdfrepostv1.service.EmailService;
import com.example.employeepdfrepostv1.service.EmployeeService;
import com.example.employeepdfrepostv1.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

//@RestController
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ReportService reportService;
    private final EmailService emailService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ReportService reportService, EmailService emailService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
        this.emailService = emailService;
    }

    @ResponseBody
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployees(){
        return employeeService.fetchEmployees();
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws JRException, FileNotFoundException {
        return reportService.export(format);
    }

    @GetMapping("/download/report")
    public ResponseEntity<byte[]> downloadReport(@RequestParam final String title) throws JRException, FileNotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=report.pdf");
        return ResponseEntity.ok().headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(reportService.downloadReport(title));
    }

    @GetMapping("/send/mail")
    @ResponseBody
    public Object sendMail() throws JRException, FileNotFoundException {
        final InputStreamSource attachment = new ByteArrayResource(reportService.downloadReport("Pepsi"));
        emailService
                .sendMessage("Testing Email",
                        "lanre.olowo@yahoo.com", "Happy are you", attachment);
        return Map.of("message", "Email sent");
    }
}
