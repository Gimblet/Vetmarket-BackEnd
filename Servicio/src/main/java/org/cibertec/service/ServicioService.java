package org.cibertec.service;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.ServicioRequestDTO;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.entity.Servicio;
import org.cibertec.entity.Usuario;
import org.cibertec.mapper.ServicioMapper;
import org.cibertec.repository.IServicioRepository;
import org.cibertec.repository.IUsuarioRepository;
import org.cibertec.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ServicioService {

    private final IServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;
    private final IUsuarioRepository usuarioRepository;

    // LISTAR SERVICIOS
    public ResponseEntity<ApiResponse<List<ServicioResponseDTO>>> listarServicio() {
        List<ServicioResponseDTO> list = servicioRepository.findAll()
                .stream()
                .map(servicioMapper::toDto)
                .toList();

        ApiResponse<List<ServicioResponseDTO>> response =
                new ApiResponse<>(true, "Lista de servicios cargada exitosamente", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // BUSCAR POR ID
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> buscarServicioPorId(Integer id) {
        return servicioRepository.findById(id)
                .map(servicio -> new ResponseEntity<>(
                        new ApiResponse<>(true, "Servicio encontrado", servicioMapper.toDto(servicio)),
                        HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(
                        new ApiResponse<>(false, "No se encontró el servicio con ID " + id, null),
                        HttpStatus.NOT_FOUND));
    }

    // CREAR SERVICIO
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> crearServicio(
            String nombre,
            String descripcion,
            Double precio,
            Long usuarioId,
            MultipartFile img) throws IOException {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el usuario con id: " + usuarioId));

        ServicioRequestDTO request = ServicioRequestDTO.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .idUsuario(usuarioId)
                .build();

        byte[] imgBytes = img != null ? img.getBytes() : null;
        Servicio servicio = servicioMapper.toEntity(request, imgBytes, usuario);
        Servicio guardado = servicioRepository.save(servicio);

        ApiResponse<ServicioResponseDTO> response =
                new ApiResponse<>(true, "Servicio creado exitosamente", servicioMapper.toDto(guardado));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ACTUALIZAR SERVICIO
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> actualizarServicio(
            Integer id,
            String nombre,
            String descripcion,
            Double precio,
            MultipartFile img) throws IOException {

        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el servicio con id: " + id));

        if (nombre != null) servicio.setNombre(nombre);
        if (descripcion != null) servicio.setDescripcion(descripcion);
        if (precio != null) servicio.setPrecio(precio);
        if (img != null && !img.isEmpty()) {
            servicio.setImg(img.getBytes());
        }

        Servicio actualizado = servicioRepository.save(servicio);
        ApiResponse<ServicioResponseDTO> response =
                new ApiResponse<>(true, "Servicio actualizado correctamente", servicioMapper.toDto(actualizado));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ELIMINAR SERVICIO
    public ResponseEntity<ApiResponse<String>> eliminarServicio(Integer id) {
        if (!servicioRepository.existsById(id)) {
            ApiResponse<String> response =
                    new ApiResponse<>(false, "No se encontró el servicio con ID " + id, "ERROR");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            servicioRepository.deleteById(id);
            ApiResponse<String> response =
                    new ApiResponse<>(true, "Servicio eliminado correctamente", "SUCCESS");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el servicio");
        }
    }

    // OBTENER IMAGEN
    public ResponseEntity<byte[]> obtenerImagen(Integer id) {
        return servicioRepository.findById(id)
                .map(servicio -> ResponseEntity.ok(servicio.getImg()))
                .orElse(ResponseEntity.notFound().build());
    }

    // LISTAR SERVICIOS POR VETERINARIO
    public ResponseEntity<ApiResponse<List<ServicioResponseDTO>>> listarServiciosPorVeterinario(Long usuarioId) {
        List<ServicioResponseDTO> list = servicioRepository.findByUsuarioIdUsuario(usuarioId)
                .stream()
                .map(servicioMapper::toDto)
                .toList();

        if (list.isEmpty()) {
            ApiResponse<List<ServicioResponseDTO>> response =
                    new ApiResponse<>(false, "No se encontraron servicios para el veterinario con ID " + usuarioId, list);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<List<ServicioResponseDTO>> response =
                new ApiResponse<>(true, "Lista de servicios del veterinario cargada exitosamente", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}