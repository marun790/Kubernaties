package arun.demo.docker.controller;

import arun.demo.docker.model.Department;
import arun.demo.docker.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dept")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<Department> getDepartment() {
        log.info("DepartmentController >> getDepartment :");
        return ResponseEntity.ok(departmentService.getDepartment());
    }
}
