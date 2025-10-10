package org.cibertec.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.MascotaRequestDto;
import org.cibertec.entity.Mascota;
import org.cibertec.service.MascotaService;
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
    public ResponseEntity<List<Mascota>> listaMascotas() {
        List<Mascota> mascotas = mascotaService.listarMascotas();
        return ResponseEntity.ok(mascotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> buscarMascotaPorId(@PathVariable Long id) {
        return mascotaService.buscarMascotaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mascota> crearMascota(@RequestBody MascotaRequestDto requestDto) {
        Mascota nuevaMascota = mascotaService.crearMascota(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMascota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizarMascota(
            @PathVariable Long id,
            @RequestBody MascotaRequestDto mascotaDto) {
        Mascota mascota = mascotaService.actualizarMascota(id, mascotaDto);
        return ResponseEntity.ok(mascota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }
}
