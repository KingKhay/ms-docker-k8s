package com.docker.k8s.departmentservice.repository;

import com.docker.k8s.departmentservice.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    Optional<Department> findByEmployeeIdsContains(UUID employeeId);

    Optional<Department> findByName(String name);
}
