package com.docker.k8s.departmentservice.service.impl;

import com.docker.k8s.departmentservice.model.Department;
import com.docker.k8s.departmentservice.repository.DepartmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save Department 201 Success")
    void test_save_department(){
        Department department = new Department();
        department.setName("IT");

        when(departmentRepository.save(any())).thenReturn(department);

        Department result = departmentService.saveDepartment(department);

        assertNotNull(result);
        assertEquals(department.getName(), result.getName());

        verify(departmentRepository, times(1)).save(any());
    }
}