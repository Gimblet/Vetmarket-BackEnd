package org.cibertec.conf;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class CustomInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("application", Map.of(
                "name", "Producto-service",
                "description", "Servicio de gestion de productos",
                "version", "1.0.0",
                "environment", System.getProperty("spring.profiles.active", "default")
        ));

        builder.withDetail("author", Map.of(
                "name", "vetmarket",
                "email", "contacto@vetmarket.com"
        ));

        builder.withDetail("runtime", Map.of(
                "java.version", System.getProperty("java.version"),
                "java.vendor", System.getProperty("java.vendor"),
                "java.home", System.getProperty("java.home"),
                "time", LocalDateTime.now().toString()
        ));
    }
}
