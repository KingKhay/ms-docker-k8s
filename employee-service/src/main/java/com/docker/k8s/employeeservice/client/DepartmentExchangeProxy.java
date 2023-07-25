package com.docker.k8s.employeeservice.client;

import com.docker.k8s.employeeservice.model.Department;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.UUID;

@HttpExchange(accept = "application/json", url = "/api/v1/departments")
public interface DepartmentExchangeProxy{

    @GetExchange("/{id}")
    Department getDepartment(@PathVariable UUID id);
}
