package com.docker.k8s.employeeservice.service.impl;

import com.docker.k8s.employeeservice.dto.EmployeeDto;
import com.docker.k8s.employeeservice.model.Employee;
import com.docker.k8s.employeeservice.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save Employee 201")
    void save_employee_success(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("Khay");

        Employee employee = new Employee();
        employee.setName("Khay");

        when(repository.save(any())).thenReturn(employee);
        when(mapper.convertValue(any(), eq(EmployeeDto.class))).thenReturn(employeeDto);

        EmployeeDto result = employeeService.saveEmployee(employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.getName(), result.getName());
    }
}