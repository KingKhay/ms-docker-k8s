package com.docker.k8s.employeeservice.controller;

import com.docker.k8s.employeeservice.dto.EmployeeDto;
import com.docker.k8s.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    EmployeeDto findEmployeeById(@PathVariable UUID id){
        return employeeService.findEmployeeById(id);
    }

    @GetMapping
    Page<EmployeeDto> findEmployees(Pageable pageable){
        return employeeService.findAllEmployees(pageable);
    }
}
