package com.docker.k8s.employeeservice.service.impl;

import com.docker.k8s.employeeservice.client.DepartmentExchangeProxy;
import com.docker.k8s.employeeservice.dto.BasicResponse;
import com.docker.k8s.employeeservice.dto.EmployeeDto;
import com.docker.k8s.employeeservice.dto.EmployeeRequest;
import com.docker.k8s.employeeservice.exception.OperationFailedException;
import com.docker.k8s.employeeservice.model.Department;
import com.docker.k8s.employeeservice.model.Employee;
import com.docker.k8s.employeeservice.repository.EmployeeRepository;
import com.docker.k8s.employeeservice.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ObjectMapper mapper;
    private final DepartmentExchangeProxy departmentExchangeProxy;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    @Transactional
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee savedEmployee = employeeRepository.save(mapper.convertValue(employeeDto, Employee.class));
        //Make call to department service to save department with user
        try{
            BasicResponse response = departmentExchangeProxy.saveEmployeeIdToDepartment(new EmployeeRequest(employeeDto.getDepartment().getName(), savedEmployee.getId()));
            logger.info(response.getMessage());
        }catch(WebClientResponseException exception){
            logger.error("Error saving employee");
            logger.error(exception.getResponseBodyAsString());
            throw new OperationFailedException("Error while saving employee");
        }
        return mapper.convertValue(savedEmployee, EmployeeDto.class);
    }

    @Override
    @Transactional
    public EmployeeDto findEmployeeById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new OperationFailedException("Could not find employee"));

        //Make call to department service to get department of user.
        Department department;
        try{
            department = departmentExchangeProxy.getDepartment(employee.getId());
        }catch(WebClientResponseException exception){
            logger.error("Error fetching employee");
            logger.error(exception.getResponseBodyAsString());
            throw new OperationFailedException("Error fetching saving employee");
        }
        EmployeeDto employeeDto = mapper.convertValue(employee, EmployeeDto.class);
        employeeDto.setDepartment(department);
        return employeeDto;
    }

    @Override
    public Page<EmployeeDto> findAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.map(employee -> mapper.convertValue(employee, EmployeeDto.class));
    }
}
