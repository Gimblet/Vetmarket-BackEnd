package org.cibertec.service;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.MascotaRequestDto;
import org.cibertec.dto.MascotaResponseDto;
import org.cibertec.entity.Mascota;
import org.cibertec.entity.Usuario;
import org.cibertec.mapper.MascotaMapper;
import org.cibertec.repository.IMascotaRepository;
import org.cibertec.repository.IUsuarioRepository;
import org.cibertec.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MascotaService {
    private final IMascotaRepository mascotaRepository;
    private final IUsuarioRepository usuarioRepository;
    private final MascotaMapper mascotaMapper;

    public ResponseEntity<ApiResponse<List<MascotaResponseDto>>> listarMascotas() {
        List<MascotaResponseDto> lista = mascotaRepository.findAll().stream()
                .map(mascotaMapper::toDto)
                .toList();
        ApiResponse<List<MascotaResponseDto>> response =
                new ApiResponse<>(true, "Lista de Mascotas cargada exitosamente", lista);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<MascotaResponseDto>> buscarMascotaPorId(Long id) {
        Optional<Mascota> mascota = mascotaRepository.findById(id);
        if (mascota.isEmpty()) {
            ApiResponse<MascotaResponseDto> response = new ApiResponse<>(false, "No se encontro el producto con ID: " + id, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        ApiResponse<MascotaResponseDto> response =
                new ApiResponse<>(true, "Producto encontrado con exito", mascotaMapper.toDto(mascota.get()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<MascotaResponseDto>> guardarMascota(MascotaRequestDto requestDto) {
        Usuario usuario = usuarioRepository.findById(requestDto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + requestDto.getIdUsuario()));

        Mascota mascota = mascotaMapper.toEntity(requestDto, usuario);
        Mascota mascotaGuardada = mascotaRepository.save(mascota);

        ApiResponse<MascotaResponseDto> response =
                new ApiResponse<>(true, "Mascota registrada exitosamente", mascotaMapper.toDto(mascotaGuardada));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<ApiResponse<MascotaResponseDto>> actualizarMascota(Long id, MascotaRequestDto requestDto) {
        Mascota mascotaExistente = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));

        mascotaExistente.setNombre(requestDto.getNombre());
        mascotaExistente.setEdad(requestDto.getEdad());
        mascotaExistente.setPeso(requestDto.getPeso());
        mascotaExistente.setEspecie(requestDto.getEspecie());
        mascotaExistente.setRaza(requestDto.getRaza());

        Mascota mascotaActualizada = mascotaRepository.save(mascotaExistente);

        ApiResponse<MascotaResponseDto> response =
                new ApiResponse<>(true, "Mascota actualizada exitosamente", mascotaMapper.toDto(mascotaActualizada));

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<List<MascotaResponseDto>>> listarMascotasPorUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            ApiResponse<List<MascotaResponseDto>> response =
                    new ApiResponse<>(false, "Usuario no encontrado con ID: " + idUsuario, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<MascotaResponseDto> lista = mascotaRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(mascotaMapper::toDto)
                .toList();

        ApiResponse<List<MascotaResponseDto>> response =
                new ApiResponse<>(true, "Lista de mascotas del usuario cargada exitosamente", lista);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Void>> eliminarMascota(Long id) {
        if (!mascotaRepository.existsById(id)) {
            ApiResponse<Void> response =
                    new ApiResponse<>(false, "Mascota no encontrada con ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        mascotaRepository.deleteById(id);

        ApiResponse<Void> response =
                new ApiResponse<>(true, "Mascota eliminada exitosamente", null);

        return ResponseEntity.ok(response);
    }

}
