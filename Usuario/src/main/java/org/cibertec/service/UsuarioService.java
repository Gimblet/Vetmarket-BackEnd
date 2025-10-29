package org.cibertec.service;



import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Rol;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UsuarioService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	
	BCryptPasswordEncoder pasEncode = new BCryptPasswordEncoder();
	

	// LISTAR TODOS LOS USUARIOS
    public List<Usuario> listarTodos() {
					
        return usuarioRepository.findAll();
    }

    // BUSCAR USUARIO POR ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    
    // BUSCAR USUARIO POR ROL
    public List<Usuario> buscarPorRol(Long idRol) {
        Rol rol=new Rol();
        rol.setIdRol(idRol);
        
        
        return usuarioRepository.findByRol(rol);
    }


    // CREAR NUEVO USUARIO
    public Usuario crearUsuario(Usuario usuario) {
    	
    	
    	usuario.setPassword(pasEncode.encode(usuario.getPassword()));
        
        Rol rol = new Rol();
        Long idRol=3L;
        
        
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
        }
        
        if (usuarioRepository.findByNumeroDocumento(usuario.getNumeroDocumento()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este documento ya existe");
        }
        
        if (usuario.getRuc() != null && !usuario.getRuc().trim().isEmpty()) {
            if (usuarioRepository.findByRuc(usuario.getRuc()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El RUC ya existe");
            }
            idRol=2L;
        }

        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo ya existe");
        }
        
        rol.setIdRol(idRol);
        usuario.setRol(rol);
    	
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Usuario nuevoUsuario) {
    	
    	
    	Usuario usuario = buscarPorId(nuevoUsuario.getIdUsuario())
    	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + nuevoUsuario.getIdUsuario()));
    	
    	Optional<Usuario> username = usuarioRepository.findByUsername(nuevoUsuario.getUsername());
    	if (username.isPresent() && !username.get().getIdUsuario().equals(nuevoUsuario.getIdUsuario())) {
    	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe.");
    	}
    	Optional<Usuario> documento = usuarioRepository.findByNumeroDocumento(nuevoUsuario.getNumeroDocumento());
    	if (documento.isPresent() && !documento.get().getIdUsuario().equals(nuevoUsuario.getIdUsuario())) {
    	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este documento ya existe.");
    	}
    	Optional<Usuario> ruc = usuarioRepository.findByRuc(nuevoUsuario.getRuc());
    	if (ruc.isPresent() && !ruc.get().getIdUsuario().equals(nuevoUsuario.getIdUsuario())) {
    	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El RUC ya existe.");
    	}
    	Optional<Usuario> correo = usuarioRepository.findByCorreo(nuevoUsuario.getCorreo());
    	if (correo.isPresent() && !correo.get().getIdUsuario().equals(nuevoUsuario.getIdUsuario())) {
    	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo ya existe.");
    	}
    	usuario.setUsername(nuevoUsuario.getUsername());
        usuario.setNumeroDocumento(nuevoUsuario.getNumeroDocumento());
        usuario.setRuc(nuevoUsuario.getRuc());
        usuario.setCorreo(nuevoUsuario.getCorreo());
        usuario.setApellido(nuevoUsuario.getApellido());
        usuario.setDireccion(nuevoUsuario.getDireccion());
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setTelefono(nuevoUsuario.getTelefono());
        if (nuevoUsuario.getPassword() != null && !nuevoUsuario.getPassword().isEmpty()) {
            usuario.setPassword(pasEncode.encode(nuevoUsuario.getPassword()));
        }
        
        try {
        	return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Algo salió mal al actualizar.");
        }
        
    }


    // ELIMINAR USUARIO
    public String eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
        	
        	 try { 
        		usuarioRepository.deleteById(id);
                return "Eliminacion exitosa";
        		 
        	 } catch (Exception e) {
                 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
             }
            
        } else {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}