package com.docker.k8s.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EmployeeRequest {

    private String department;
    private UUID employeeId;
}
