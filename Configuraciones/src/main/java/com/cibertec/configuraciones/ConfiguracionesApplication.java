package com.cibertec.configuraciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfiguracionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfiguracionesApplication.class, args);
    }

}
