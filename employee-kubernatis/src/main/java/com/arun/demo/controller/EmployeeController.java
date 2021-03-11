package com.arun.demo.controller;

import com.arun.demo.core.EmployeeService;
import com.arun.demo.model.Employee;
import com.arun.demo.model.external.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by GBS03785 on 2/27/2021.
 */
@RestController
@RequestMapping("employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "all")
    public ResponseEntity<List<Employee>> getEmployees() {
        log.info("EmployeeController >> getEmployees :");
        return ResponseEntity.ok(employeeService.getAll());
    }


    @GetMapping(value = "department")
    public ResponseEntity<Department> getDepartments() {
        log.info("EmployeeController >> getDepartments :");
        return ResponseEntity.ok(employeeService.getDepartment());
    }


}
