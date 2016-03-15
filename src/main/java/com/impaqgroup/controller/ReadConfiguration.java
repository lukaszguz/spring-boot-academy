package com.impaqgroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadConfiguration {

    private Environment environment;

    @Autowired
    public ReadConfiguration(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping("/read-properties/{propertyName}")
    public String readProperies(@PathVariable String propertyName) {
        return environment.getProperty(propertyName, "Not found");
    }
}