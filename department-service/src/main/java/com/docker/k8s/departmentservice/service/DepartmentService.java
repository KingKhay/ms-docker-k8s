package com.docker.k8s.departmentservice.service;

import com.docker.k8s.departmentservice.model.Department;

import java.util.UUID;

public interface DepartmentService {

    Department findDepartmentOfEmployee(UUID employeeId);

    Department saveDepartment(Department department);
}
