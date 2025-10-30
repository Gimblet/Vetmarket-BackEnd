package org.cibertec.controller;

import java.util.List;

import org.cibertec.entity.Usuario;
import org.cibertec.service.UsuarioService;
import org.cibertec.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;


    // LISTAR TODOS LOS USUARIOS
    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> listaUsuarios() {
        return usuarioService.listarTodos();
    }

    // BUSCAR USUARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }


    // BUSCAR USUARIOS POR ROL
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<ApiResponse<List<Usuario>>> buscarUsuariosPorRol(@PathVariable Long idRol) {
        return usuarioService.buscarPorRol(idRol);
    }


    // CREAR NUEVO USUARIO
    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    // ACTUALIZAR USUARIO
    @PutMapping
    public ResponseEntity<ApiResponse<Usuario>> actualizarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(usuario);
    }

    // ELIMINAR USUARIO
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id);
    }

}