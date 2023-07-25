package com.docker.k8s.employeeservice.dto;

import com.docker.k8s.employeeservice.model.Department;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeDto{

    private UUID id;

    @NotNull
    private String name;

    private String address;

    private Department department;
}
