package org.cibertec.service;



import java.util.List;
import java.util.Optional;
import org.cibertec.dto.UsuarioRequestDTO;
import org.cibertec.entity.Rol;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UsuarioService {
	@Autowired
    private UsuarioRepository usuarioRepository;
	

	// LISTAR TODOS LOS USUARIOS
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // BUSCAR USUARIO POR ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }


    // CREAR NUEVO USUARIO
    public Usuario crearUsuario(UsuarioRequestDTO requestDto) {
        // Verificar que el username no esté usado
        if (usuarioRepository.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("El username ya está en uso: " + requestDto.getUsername());
        }
        
        Rol rol = Rol.builder()
        		.idRol(requestDto.getIdRol()).build();
        
        // Crear el nuevo usuario
        Usuario usuario = Usuario.builder()
                .nombre(requestDto.getNombre())
                .apellido(requestDto.getApellido())
                .numeroDocumento(requestDto.getNumeroDocumento())
                .telefono(requestDto.getTelefono())
                .direccion(requestDto.getDireccion())
                .correo(requestDto.getCorreo())
                .ruc(requestDto.getRuc())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .rol(rol)
                .build();

        return usuarioRepository.save(usuario);
    }

    // ACTUALIZAR USUARIO
    public Usuario actualizarUsuario(Long id, UsuarioRequestDTO requestDto) {
        // Buscar el usuario existente
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        // Verificar que el nuevo username no esté usado por otro usuario
        Optional<Usuario> usuarioConMismoUsername = usuarioRepository.findByUsername(requestDto.getUsername());
        if (usuarioConMismoUsername.isPresent() && !usuarioConMismoUsername.get().getIdUsuario().equals(id)) {
            throw new RuntimeException("El username ya está en uso por otro usuario: " + requestDto.getUsername());
        }
        Rol rol = Rol.builder()
        		.idRol(requestDto.getIdRol()).build();
        // Actualizar los datos del usuario
        usuarioExistente.setNombre(requestDto.getNombre());
        usuarioExistente.setApellido(requestDto.getApellido());
        usuarioExistente.setNumeroDocumento(requestDto.getNumeroDocumento());
        usuarioExistente.setTelefono(requestDto.getTelefono());
        usuarioExistente.setDireccion(requestDto.getDireccion());
        usuarioExistente.setCorreo(requestDto.getCorreo());
        usuarioExistente.setRuc(requestDto.getRuc());
        usuarioExistente.setPassword(requestDto.getPassword());
        usuarioExistente.setRol(rol);
        

        // Solo actualizar la contraseña si se envió una nueva
        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            usuarioExistente.setPassword(requestDto.getPassword());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    // ELIMINAR USUARIO
    public void eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
    }

    // BUSCAR USUARIOS POR ROL
    public List<Usuario> buscarPorRol(Long idRol) {
        return usuarioRepository.findByRolIdRol(idRol);
    }

}