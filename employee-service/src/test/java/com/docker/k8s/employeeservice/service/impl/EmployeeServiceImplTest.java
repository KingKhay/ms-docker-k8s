package com.docker.k8s.employeeservice.service.impl;

import com.docker.k8s.employeeservice.client.DepartmentExchangeProxy;
import com.docker.k8s.employeeservice.dto.BasicResponse;
import com.docker.k8s.employeeservice.dto.EmployeeDto;
import com.docker.k8s.employeeservice.exception.OperationFailedException;
import com.docker.k8s.employeeservice.model.Employee;
import com.docker.k8s.employeeservice.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;

    @Mock
    private DepartmentExchangeProxy departmentExchangeProxy;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save Employee 201")
    void save_employee_success() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("Khay");

        Employee employee = new Employee();
        employee.setName("Khay");

        when(repository.save(any())).thenReturn(employee);
        when(mapper.convertValue(any(), eq(EmployeeDto.class))).thenReturn(employeeDto);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("Employee Added successfully");

        given(webClient.method(HttpMethod.POST)
                .uri(anyString(), anyMap())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BasicResponse>() {
                })).willReturn(Mono.just(basicResponse));

//        when(departmentExchangeProxy.saveEmployeeIdToDepartment(any())).thenReturn(basicResponse);

        EmployeeDto result = employeeService.saveEmployee(employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.getName(), result.getName());
    }

    @Test
    @DisplayName("Find All Employees 200")
    void find_all_employees() {
        Pageable pageable = Pageable.unpaged();

        Employee employee1 = new Employee();
        employee1.setName("King");
        Employee employee2 = new Employee();
        employee2.setName("Khay");

        List<Employee> employees = List.of(employee1, employee2);

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setName("King");
        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setName("Khay");

        Page<Employee> pageOfEmployees = new PageImpl<>(employees);

        when(repository.findAll(any(Pageable.class))).thenReturn(pageOfEmployees);
        when(mapper.convertValue(any(Employee.class), eq(EmployeeDto.class))).thenReturn(employeeDto1, employeeDto2);

        Page<EmployeeDto> result = employeeService.findAllEmployees(pageable);

        assertNotNull(result);
        assertEquals(2, result.getSize());

        verify(repository, times(1)).findAll(pageable);
        verify(mapper, times(2)).convertValue(any(), eq(EmployeeDto.class));
    }

    @Test
    @DisplayName("Test Find All Employees, Empty Page")
    void testFindAllEmployees_Empty(){
        Page<Employee> emptyPage = new PageImpl<>(Collections.emptyList());

        when(repository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        Page<EmployeeDto> result = employeeService.findAllEmployees(PageRequest.of(0, 10));

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    @Test()
    @DisplayName("Test Find All Employees, Pagination scenarios")
    void testFindAllEmployees_Pagination(){

        Employee employee1 = new Employee();
        employee1.setName("King");
        Employee employee2 = new Employee();
        employee2.setName("Khay");

        List<Employee> employees = List.of(employee1, employee2);

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setName("King");
        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setName("Khay");

        List<Employee> employeesList = List.of(employee1, employee2);

        Pageable pageable1 = PageRequest.of(0, 10);
        Pageable pageable2 = PageRequest.of(1, 10);
        Pageable pageable3 = PageRequest.of(2, 10);

        when(repository.findAll(pageable1)).thenReturn(new PageImpl<>(employeesList));
        when(repository.findAll(pageable2)).thenReturn(new PageImpl<>(Collections.emptyList()));
        when(repository.findAll(pageable3)).thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<EmployeeDto> result1 = employeeService.findAllEmployees(pageable1);
        Page<EmployeeDto> result2 = employeeService.findAllEmployees(pageable2);
        Page<EmployeeDto> result3 = employeeService.findAllEmployees(pageable3);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);

        verify(repository, times(3)).findAll(any(Pageable.class));
        verify(repository, times(1)).findAll(pageable1);
        verify(repository, times(1)).findAll(pageable2);
        verify(repository, times(1)).findAll(pageable3);
    }
}