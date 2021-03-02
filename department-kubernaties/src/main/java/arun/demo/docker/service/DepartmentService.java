package arun.demo.docker.service;

import arun.demo.docker.model.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    public Department getDepartment() {
        return new Department(1L, "CSE");
    }
}
