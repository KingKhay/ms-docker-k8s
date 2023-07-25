package com.docker.k8s.departmentservice.seeder;

import com.docker.k8s.departmentservice.model.Department;
import com.docker.k8s.departmentservice.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetupDataLoader implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(SetupDataLoader.class);

    boolean alreadySetup = false;


    @Override
    public void run(String... args) throws Exception {
        if (alreadySetup) {
            return;
        }

        Department itDepartment = Department.builder()
                .name("IT")
                .build();

        Department marketingDepartment = Department.builder()
                .name("Marketing")
                .build();

        try {
            departmentRepository.saveAll(List.of(itDepartment, marketingDepartment));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        alreadySetup = true;
    }
}
