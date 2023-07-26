package com.docker.k8s.departmentservice.service;

import com.docker.k8s.departmentservice.dto.BasicResponse;
import com.docker.k8s.departmentservice.dto.EmployeeRequest;
import com.docker.k8s.departmentservice.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DepartmentService {

    Department findDepartmentOfEmployee(UUID employeeId);

    Department saveDepartment(Department department);

    Department findDepartmentById(UUID id);

    Page<Department> findAllDepartments(Pageable pageable);

    BasicResponse addEmployeeToDepartment(EmployeeRequest employeeRequest);
}
