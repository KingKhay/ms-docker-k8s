package com.docker.k8s.employeeservice.client;

import com.docker.k8s.employeeservice.dto.BasicResponse;
import com.docker.k8s.employeeservice.dto.EmployeeRequest;
import com.docker.k8s.employeeservice.model.Department;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.UUID;

@HttpExchange(accept = "application/json", url = "/api/v1/departments")
public interface DepartmentExchangeProxy{

    @GetExchange("/employee/{id}")
    Department getDepartment(@PathVariable UUID id);

    @PostExchange("/employee")
    BasicResponse saveEmployeeIdToDepartment(@RequestBody EmployeeRequest employeeRequest);
}
