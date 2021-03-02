package com.arun.demo.proxy;

import com.arun.demo.model.external.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "department-service-proxy", url = "${department-uri}")
public interface DepartmentProxy {

    @GetMapping(value = "dept")
    Department getDepartment();

}
