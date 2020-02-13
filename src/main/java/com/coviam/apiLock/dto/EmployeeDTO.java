package com.coviam.apiLock.dto;

import lombok.Data;

import javax.annotation.security.DenyAll;

@Data
public class EmployeeDTO {
    private int id;
    private String name;
    private double salary;
}
