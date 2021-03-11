package com.arun.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("util")
@Slf4j
public class UtilController {

    @Value("${address}")
    private String address;
    @Value("${application-name}")
    private String applicationNameFromConfigMap;

    @Value("${secretName}")
    private String secretName;

    @GetMapping("getAddress")
    private String getValueFromConfigmap() {
        log.info("UtilController >> getValueFromConfigmap >> address :" + address);
        return address;
    }

    @GetMapping("getApplicationName")
    private String getApplicationNameFromConfigmap() {
        return applicationNameFromConfigMap;
    }

    @GetMapping
    private String getSecretValue() {
        return secretName;
    }
}
