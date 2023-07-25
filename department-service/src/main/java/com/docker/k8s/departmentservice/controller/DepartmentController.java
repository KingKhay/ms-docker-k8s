package com.docker.k8s.departmentservice.controller;

import com.docker.k8s.departmentservice.model.Department;
import com.docker.k8s.departmentservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/{id}")
    Department getDepartmentOfEmployee(@PathVariable UUID id){
        return departmentService.findDepartmentOfEmployee(id);
    }

    @PostMapping
    Department saveDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/hello")
    String helloWorld(){
        return "Connected Successfully";
    }
}
