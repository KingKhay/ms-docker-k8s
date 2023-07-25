package com.docker.k8s.employeeservice.repository;

import com.docker.k8s.employeeservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
