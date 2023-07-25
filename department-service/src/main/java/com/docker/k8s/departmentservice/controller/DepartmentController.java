package com.docker.k8s.departmentservice.controller;

import com.docker.k8s.departmentservice.model.Department;
import com.docker.k8s.departmentservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/employee/{id}")
    Department getDepartmentOfEmployee(@PathVariable UUID id){
        return departmentService.findDepartmentOfEmployee(id);
    }

    @PostMapping
    Department saveDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/{id}")
    Department findDepartmentById(@PathVariable UUID id){
        return departmentService.findDepartmentById(id);
    }

    @GetMapping()
    Page<Department> findAllDepartments(Pageable pageable){
        return departmentService.findAllDepartments(pageable);
    }

    @GetMapping("/hello")
    String helloWorld(){
        return "Connected Successfully";
    }
}
