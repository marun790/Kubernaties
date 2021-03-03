package com.arun.demo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("util")
public class UtilController {

    @Value("${address}")
    private String address;
    @Value("${application-name}")
    private String applicationNameFromConfigMap;

    @GetMapping("getAddress")
    private String getValueFromConfigmap() {
        return address;
    }

        @GetMapping("getApplicationName")
    private String getApplicationNameFromConfigmap() {
        return applicationNameFromConfigMap;
    }

}
