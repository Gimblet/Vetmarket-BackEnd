package org.cibertec.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.MascotaRequestDto;
import org.cibertec.dto.MascotaResponseDto;
import org.cibertec.entity.Mascota;
import org.cibertec.service.MascotaService;
import org.cibertec.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
@RequiredArgsConstructor
public class MascotaController {
    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MascotaResponseDto>>> listaMascotas() {
        return mascotaService.listarMascotas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MascotaResponseDto>> buscarMascotaPorId(@PathVariable Long id) {
        return mascotaService.buscarMascotaPorId(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MascotaResponseDto>> guardarMascota(@RequestBody MascotaRequestDto requestDto) {
        return mascotaService.guardarMascota(requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MascotaResponseDto>> actualizarMascota(
            @PathVariable Long id,
            @RequestBody MascotaRequestDto requestDto) {
        return mascotaService.actualizarMascota(id, requestDto);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<MascotaResponseDto>>> listarMascotasPorUsuario(
            @PathVariable Long idUsuario) {
        return mascotaService.listarMascotasPorUsuario(idUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarMascota(@PathVariable Long id) {
        return mascotaService.eliminarMascota(id);
    }
}
