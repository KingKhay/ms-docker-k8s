package com.docker.k8s.departmentservice.service.impl;

import com.docker.k8s.departmentservice.dto.BasicResponse;
import com.docker.k8s.departmentservice.dto.EmployeeRequest;
import com.docker.k8s.departmentservice.exception.EntityNotFoundException;
import com.docker.k8s.departmentservice.model.Department;
import com.docker.k8s.departmentservice.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;

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

    @Test
    @DisplayName("Find_Department By Id Not Found")
    void test_find_department_by_id_not_found(){
        UUID departmentId = UUID.randomUUID();

        when(departmentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            departmentService.findDepartmentById(departmentId);
        });
    }

    @Test
    @DisplayName("Add Employee To Department Success")
    void test_add_employee_to_department(){
        UUID employeeId = UUID.randomUUID();
        String departmentName = "IT";

        Department department = new Department();
        department.setName(departmentName);

        EmployeeRequest employeeRequest = new EmployeeRequest(departmentName, employeeId);

        when(departmentRepository.findByName(anyString())).thenReturn(Optional.of(department));

        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        BasicResponse basicResponse = departmentService.addEmployeeToDepartment(employeeRequest);

        assertNotNull(basicResponse);
        verify(departmentRepository, times(1)).findByName(anyString());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    @DisplayName("Add Employee To Department Not Found")
    void test_add_employee_to_department_not_found(){
        UUID employeeId = UUID.randomUUID();
        String departmentName = "IT";

        Department department = new Department();
        department.setName(departmentName);

        EmployeeRequest employeeRequest = new EmployeeRequest(departmentName, employeeId);

        when(departmentRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            departmentService.addEmployeeToDepartment(employeeRequest);
        });

        verify(departmentRepository, times(1)).findByName(anyString());
        verify(departmentRepository, never()).save(any(Department.class));
    }
}