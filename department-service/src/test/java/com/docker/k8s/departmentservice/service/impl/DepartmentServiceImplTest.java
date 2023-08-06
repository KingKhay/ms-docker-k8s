package com.docker.k8s.departmentservice.service.impl;

import com.docker.k8s.departmentservice.exception.EntityNotFoundException;
import com.docker.k8s.departmentservice.model.Department;
import com.docker.k8s.departmentservice.repository.DepartmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;

    private ObjectMapper mapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save Department 201 Success")
    void test_save_department() {
        Department department = new Department();
        department.setName("IT");

        when(departmentRepository.save(any())).thenReturn(department);

        Department result = departmentService.saveDepartment(department);

        assertNotNull(result);
        assertEquals(department.getName(), result.getName());

        verify(departmentRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Find Department Of Employee")
    void test_find_department_of_employee() {
        UUID employeeId = UUID.randomUUID();

        Department foundDepartment = new Department();
        foundDepartment.setName("IT");

        Optional<Department> department = Optional.of(foundDepartment);
        when(departmentRepository.findByEmployeeIdsContains(any(UUID.class))).thenReturn(department);

        Department result = departmentService.findDepartmentOfEmployee(employeeId);

        assertNotNull(result);
        assertEquals(foundDepartment.getName(), result.getName());

        verify(departmentRepository, times(1)).findByEmployeeIdsContains(employeeId);
    }

    @Test
    @DisplayName("Find Department of Employee Not Found")
    void test_find_department_of_employee_not_found() {
        UUID employeeId = UUID.randomUUID();

        when(departmentRepository.findByEmployeeIdsContains(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            departmentService.findDepartmentOfEmployee(employeeId);
        });

        verify(departmentRepository, times(1)).findByEmployeeIdsContains(employeeId);
    }

    @Test
    @DisplayName("Find_Department By Id Success")
    void test_find_department_by_id() {
        UUID departmentId = UUID.randomUUID();

        Department department = new Department();
        department.setId(departmentId);

        when(departmentRepository.findById(any(UUID.class))).thenReturn(Optional.of(department));

        Department result = departmentService.findDepartmentById(departmentId);

        assertNotNull(result);
        assertEquals(departmentId, result.getId());
    }

}