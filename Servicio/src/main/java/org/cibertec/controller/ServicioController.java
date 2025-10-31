package org.cibertec.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.service.ServicioService;
import org.cibertec.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    // LISTAR SERVICIOS
    @GetMapping
    public ResponseEntity<ApiResponse<List<ServicioResponseDTO>>> listarServicios() {
        return servicioService.listarServicio();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> buscarServicioPorId(@PathVariable Integer id) {
        return servicioService.buscarServicioPorId(id);
    }

    // CREAR SERVICIO
    @PostMapping
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> crearServicio(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam Double precio,
            @RequestParam Long usuarioId,
            @RequestParam(required = false) MultipartFile img) throws IOException {
        return servicioService.crearServicio(nombre, descripcion, precio, usuarioId, img);
    }

    // ACTUALIZAR SERVICIO
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> actualizarServicio(
            @PathVariable Integer id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Double precio,
            @RequestParam(required = false) MultipartFile img) throws IOException {
        return servicioService.actualizarServicio(id, nombre, descripcion, precio, img);
    }

    // ELIMINAR SERVICIO
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarServicio(@PathVariable Integer id) {
        return servicioService.eliminarServicio(id);
    }

    // OBTENER IMAGEN
    @GetMapping(value = "/imagen/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Integer id) {
        return servicioService.obtenerImagen(id);
    }

    // LISTAR POR VETERINARIO
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<ServicioResponseDTO>>> listarServiciosPorVeterinario(
            @PathVariable Long idUsuario) {
        return servicioService.listarServiciosPorVeterinario(idUsuario);
    }
}