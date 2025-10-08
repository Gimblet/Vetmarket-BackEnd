package org.cibertec.web.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.application.service.IMascotaService;
import org.cibertec.web.dto.MascotaRequestDto;
import org.cibertec.web.dto.MascotaResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
@RequiredArgsConstructor
public class MascotaController {
    private final IMascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<MascotaResponseDto>> listarMascotas() {
        List<MascotaResponseDto> mascotas = mascotaService.listarMascotas();
        return ResponseEntity.ok(mascotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDto> buscarMascotaPorId(@PathVariable Long id) {
        MascotaResponseDto mascota = mascotaService.buscarMascotaPorId(id);
        return ResponseEntity.ok(mascota);
    }

    @PostMapping
    public ResponseEntity<MascotaResponseDto> crearMascota(@RequestBody MascotaRequestDto requestDto) {
        MascotaResponseDto mascota = mascotaService.guardarMascota(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mascota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDto> actualizarMascota(
            @PathVariable Long id,
            @RequestBody MascotaRequestDto requestDto
    ) {
        MascotaResponseDto mascota = mascotaService.actualizarMascota(id, requestDto);
        return ResponseEntity.ok(mascota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }
}
