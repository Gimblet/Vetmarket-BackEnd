package org.cibertec.controller;

import java.util.List;
import org.cibertec.dto.UsuarioRequestDTO;
import org.cibertec.entity.Usuario;
import org.cibertec.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	    public ResponseEntity<List<Usuario>> listaUsuarios() {
	        List<Usuario> usuarios = usuarioService.listarTodos();
	        return ResponseEntity.ok(usuarios);
	    }

	    // BUSCAR USUARIO POR ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
	        return usuarioService.buscarPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }


	    // CREAR NUEVO USUARIO
	    @PostMapping
	    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioRequestDTO requestDto) {
	        Usuario nuevoUsuario = usuarioService.crearUsuario(requestDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
	    }

	    // ACTUALIZAR USUARIO
	    @PutMapping("/{id}")
	    public ResponseEntity<Usuario> actualizarUsuario(
	            @PathVariable Long id,
	            @RequestBody UsuarioRequestDTO usuarioDto) {
	        Usuario usuario = usuarioService.actualizarUsuario(id, usuarioDto);
	        return ResponseEntity.ok(usuario);
	    }

	    // ELIMINAR USUARIO
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
	        usuarioService.eliminarUsuario(id);
	        return ResponseEntity.noContent().build();
	    }

	    
	    // BUSCAR USUARIOS POR ROL
	    @GetMapping("/rol/{idRol}")
	    public ResponseEntity<List<Usuario>> buscarUsuariosPorRol(@PathVariable Long idRol) {
	        List<Usuario> usuarios = usuarioService.buscarPorRol(idRol);
	        return ResponseEntity.ok(usuarios);
	    }

	}