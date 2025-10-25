package com.cibertec.email.client;

import org.cibertec.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Usuario", url = "localhost:8080/usuario/usuario")
public interface IUsuarioClient {

    @GetMapping("/{id}")
    Usuario getById(@PathVariable Long id);
}
