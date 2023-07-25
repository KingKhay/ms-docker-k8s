package com.docker.k8s.employeeservice.service.impl;

import com.docker.k8s.employeeservice.client.DepartmentExchangeProxy;
import com.docker.k8s.employeeservice.dto.EmployeeDto;
import com.docker.k8s.employeeservice.exception.OperationFailedException;
import com.docker.k8s.employeeservice.model.Department;
import com.docker.k8s.employeeservice.model.Employee;
import com.docker.k8s.employeeservice.repository.EmployeeRepository;
import com.docker.k8s.employeeservice.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ObjectMapper mapper;
    private final DepartmentExchangeProxy departmentExchangeProxy;

    @Override
    @Transactional
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee savedEmployee = employeeRepository.save(mapper.convertValue(employeeDto, Employee.class));
        //Make call to department service to save department with user

        return mapper.convertValue(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto findEmployeeById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new OperationFailedException("Could not find employee"));

        //Make call to department service to get department of user.
        Department department = departmentExchangeProxy.getDepartment(employee.getId());
        EmployeeDto employeeDto = mapper.convertValue(employee, EmployeeDto.class);
        employeeDto.setDepartment(department);
        return employeeDto;
    }
}
