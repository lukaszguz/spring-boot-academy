package com.impaqgroup.controller;

import com.impaqgroup.domain.MyConfigurationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationViewController {

    private MyConfigurationSettings configurationSettings;

    @Autowired
    public ConfigurationViewController(MyConfigurationSettings configurationSettings) {
        this.configurationSettings = configurationSettings;
    }

    @RequestMapping("show-configuration-properties")
    public MyConfigurationSettings showConfigurationProperties() {
        return configurationSettings;
    }
}