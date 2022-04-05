package com.example.employeepdfrepostv1.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Employee {

    @Id
    private int id;
    private String name;
    private String email;
    private String phone;
    private String jobTitle;

}
