package org.cibertec.controller;

import java.util.List;

import org.cibertec.entity.Usuario;
import org.cibertec.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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
    
    
    // BUSCAR USUARIOS POR ROL
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorRol(@PathVariable Long idRol) {
    	
    	List<Usuario> usuarios = usuarioService.buscarPorRol(idRol);
        return ResponseEntity.ok(usuarios);
    }


    // CREAR NUEVO USUARIO
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
    	
    	try {
	        Usuario nuevo = usuarioService.crearUsuario(usuario);
	        return ResponseEntity.ok(nuevo);
	    } catch (Exception e) {
	    	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Algo salio mal al registrar usuario");
	    }
    }

    // ACTUALIZAR USUARIO
    @PutMapping
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
    	
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario); 
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Algo salió mal al actualizar.");
        }
    }

    // ELIMINAR USUARIO
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
       
    	
        String mensaje = usuarioService.eliminarUsuario(id);
        
        if(!mensaje.equals("Error")) {	
        	  return ResponseEntity.ok(mensaje);        	
        }else {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Algo salió mal al eliminar.");
       	
        }
        
    }

    


}