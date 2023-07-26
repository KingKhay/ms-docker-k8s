package com.docker.k8s.departmentservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    @ElementCollection
    private List<UUID> employeeIds;

    public void addEmployeeId(UUID id){
        if(employeeIds == null){
            employeeIds = new ArrayList<>();
        }
        employeeIds.add(id);
    }
}
