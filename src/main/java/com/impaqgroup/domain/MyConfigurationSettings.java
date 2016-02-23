package com.impaqgroup.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "my_configuration")
public class MyConfigurationSettings {

    @NotNull
    @Valid
    private ServerConfiguration serverConfiguration;

    public ServerConfiguration getServerConfiguration() {
        return serverConfiguration;
    }

    public void setServerConfiguration(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }

    public static class ServerConfiguration {
        @NotNull
        public Integer port;

        @NotEmpty
        public String serverName;

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        public String getServerName() {

            return serverName;
        }
    }
}