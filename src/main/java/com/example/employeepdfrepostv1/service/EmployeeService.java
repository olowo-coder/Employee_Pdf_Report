package com.example.employeepdfrepostv1.service;

import com.example.employeepdfrepostv1.entity.Employee;
import com.example.employeepdfrepostv1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> fetchEmployees(){
        return employeeRepository.findAll();
    }
}
