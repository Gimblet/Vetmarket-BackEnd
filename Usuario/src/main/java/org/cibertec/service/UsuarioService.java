package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Rol;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.UsuarioRepository;
import org.cibertec.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    BCryptPasswordEncoder pasEncode = new BCryptPasswordEncoder();


    // LISTAR TODOS LOS USUARIOS
    public ResponseEntity<ApiResponse<List<Usuario>>> listarTodos() {
        List<Usuario> list = usuarioRepository.findAll();
        ApiResponse<List<Usuario>> response =
                new ApiResponse<>(true, "Lista de Usuarios cargada exitosamente", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // BUSCAR USUARIO POR ID
    public ResponseEntity<ApiResponse<Usuario>> buscarPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "No se pudo encontrar el Usuario con ID " + id, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioRepository.findById(id).get();

        ApiResponse<Usuario> response =
                new ApiResponse<>(true, "Usuario encontrado", usuario);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // BUSCAR USUARIO POR ROL
    public ResponseEntity<ApiResponse<List<Usuario>>> buscarPorRol(Long idRol) {
        Rol rol = new Rol();
        rol.setIdRol(idRol);

        List<Usuario> list = usuarioRepository.findByRol(rol);

        if (list.isEmpty()) {
            ApiResponse<List<Usuario>> response =
                    new ApiResponse<>(false, "No se encontró ningun usuario con el Rol", list);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<List<Usuario>> response =
                new ApiResponse<>(true, "Lista de Usuarios por Rol cargada exitosamente", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // CREAR NUEVO USUARIO
    public ResponseEntity<ApiResponse<Usuario>> crearUsuario(Usuario usuario) {
        usuario.setPassword(pasEncode.encode(usuario.getPassword()));

        Rol rol = new Rol();
        Long idRol = 3L;


        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "Ya existe un usuario con ese nombre", usuario);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        if (usuarioRepository.findByNumeroDocumento(usuario.getNumeroDocumento()).isPresent()) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "Ya existe un usuario con ese número de documento", usuario);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        if (usuario.getRuc() != null && !usuario.getRuc().trim().isEmpty()) {
            if (usuarioRepository.findByRuc(usuario.getRuc()).isPresent()) {
                ApiResponse<Usuario> response =
                        new ApiResponse<>(false, "Ya existe un veterinario con ese número de RUC", usuario);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            idRol = 2L;
        }

        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "Ya existe un usuario con ese correo electronico", usuario);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        rol.setIdRol(idRol);
        usuario.setRol(rol);

        ApiResponse<Usuario> response =
                new ApiResponse<>(true, "Usuario creado correctamente", usuario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<Usuario>> actualizarUsuario(Usuario nuevoUsuario) {

        Optional<Usuario> usuario = usuarioRepository.findById(nuevoUsuario.getIdUsuario());

        if (usuario.isEmpty()) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "No se pudo encontrar el Usuario con ID " + nuevoUsuario.getIdUsuario(), nuevoUsuario);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Usuario> username = usuarioRepository.findByUsername(nuevoUsuario.getUsername());
        if (username.isPresent() && !username.get().getIdUsuario().equals(nuevoUsuario.getIdUsuario())) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "El usuario ingresado ya existe" + nuevoUsuario.getIdUsuario(), nuevoUsuario);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> documento = usuarioRepository.findByNumeroDocumento(nuevoUsuario.getNumeroDocumento());
        if (documento.isPresent() && !documento.get().getIdUsuario().equals(nuevoUsuario.getIdUsuario())) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "El documento ingresado ya existe" + nuevoUsuario.getIdUsuario(), nuevoUsuario);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> ruc = usuarioRepository.findByRuc(nuevoUsuario.getRuc());
        if (ruc.isPresent() && !ruc.get().getIdUsuario().equals(nuevoUsuario.getIdUsuario())) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "El RUC ingresado ya existe" + nuevoUsuario.getIdUsuario(), nuevoUsuario);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> correo = usuarioRepository.findByCorreo(nuevoUsuario.getCorreo());
        if (correo.isPresent() && !correo.get().getIdUsuario().equals(nuevoUsuario.getIdUsuario())) {
            ApiResponse<Usuario> response =
                    new ApiResponse<>(false, "El correo ingresado ya existe" + nuevoUsuario.getIdUsuario(), nuevoUsuario);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        usuario.get().setUsername(nuevoUsuario.getUsername());
        usuario.get().setNumeroDocumento(nuevoUsuario.getNumeroDocumento());
        usuario.get().setRuc(nuevoUsuario.getRuc());
        usuario.get().setCorreo(nuevoUsuario.getCorreo());
        usuario.get().setApellido(nuevoUsuario.getApellido());
        usuario.get().setDireccion(nuevoUsuario.getDireccion());
        usuario.get().setNombre(nuevoUsuario.getNombre());
        usuario.get().setTelefono(nuevoUsuario.getTelefono());
        if (nuevoUsuario.getPassword() != null && !nuevoUsuario.getPassword().isEmpty()) {
            usuario.get().setPassword(pasEncode.encode(nuevoUsuario.getPassword()));
        }

        try {
            Usuario usuarioSaved = usuarioRepository.save(usuario.get());
            ApiResponse<Usuario> response =
                    new ApiResponse<>(true, "Usuario Actualizado Correctamente", usuarioSaved);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Algo salió mal al actualizar.");
        }
    }

    // ELIMINAR USUARIO
    public ResponseEntity<ApiResponse<String>> eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            ApiResponse<String> response =
                    new ApiResponse<>(false, "No se pudo encontrar el Usuario con ID " + id, "ERROR");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            usuarioRepository.deleteById(id);
            ApiResponse<String> response =
                    new ApiResponse<>(true, "Usuario Eliminado correctamente" + id, "SUCCESS");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}