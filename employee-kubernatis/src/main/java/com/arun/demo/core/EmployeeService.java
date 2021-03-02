package com.arun.demo.core;

import com.arun.demo.model.Employee;
import com.arun.demo.model.external.Department;
import com.arun.demo.proxy.DepartmentProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by GBS03785 on 2/27/2021.
 */
@Service
public class EmployeeService {

    @Autowired
    private DepartmentProxy departmentProxy;

    private static List<Employee> EMPLOYEES = Arrays.asList(
            new Employee(1L, "Arun", "IT"),
            new Employee(2L, "Anbu", "IT"),
            new Employee(3L, "Muthu", "IT")

    );

    public List<Employee> getAll() {
        return EMPLOYEES;
    }

    public Department getDepartment() {
        return departmentProxy.getDepartment();
    }
}
