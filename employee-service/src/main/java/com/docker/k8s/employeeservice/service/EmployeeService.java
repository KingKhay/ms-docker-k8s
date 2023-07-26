package com.docker.k8s.employeeservice.service;



import com.docker.k8s.employeeservice.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    EmployeeDto findEmployeeById(UUID id);

    Page<EmployeeDto> findAllEmployees(Pageable pageable);
}
