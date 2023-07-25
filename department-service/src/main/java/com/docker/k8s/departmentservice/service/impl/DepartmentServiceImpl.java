package com.docker.k8s.departmentservice.service.impl;

import com.docker.k8s.departmentservice.exception.EntityNotFoundException;
import com.docker.k8s.departmentservice.model.Department;
import com.docker.k8s.departmentservice.repository.DepartmentRepository;
import com.docker.k8s.departmentservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    @Override
    public Department findDepartmentOfEmployee(UUID employeeId) {
        return departmentRepository.findByEmployeeIdsContains(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("No such department"));
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department findDepartmentById(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such department"));
    }

    @Override
    public Page<Department> findAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }
}
