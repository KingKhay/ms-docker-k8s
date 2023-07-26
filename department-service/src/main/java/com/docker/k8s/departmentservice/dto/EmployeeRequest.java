package com.docker.k8s.departmentservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeRequest {

    private String department;
    private UUID employeeId;
}
