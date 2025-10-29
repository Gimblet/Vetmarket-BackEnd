package org.cibertec.service;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.MascotaRequestDto;
import org.cibertec.entity.Mascota;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.IMascotaRepository;
import org.cibertec.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MascotaService {
    private final IMascotaRepository mascotaRepository;
    private final IUsuarioRepository usuarioRepository;

    public List<Mascota> listarMascotas() {
        return mascotaRepository.findAll();
    }

    public Optional<Mascota> buscarMascotaPorId(Long id) {
        return mascotaRepository.findById(id);
    }

    public Mascota crearMascota(MascotaRequestDto requestDto) {
        Usuario usuario = usuarioRepository.findById(requestDto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + requestDto.getIdUsuario()));

        Mascota mascota = Mascota.builder()
                .nombre(requestDto.getNombre())
                .edad(requestDto.getEdad())
                .peso(requestDto.getPeso())
                .especie(requestDto.getEspecie())
                .raza(requestDto.getRaza())
                .usuario(usuario)
                .build();

        return mascotaRepository.save(mascota);
    }

    public Mascota actualizarMascota(Long id, MascotaRequestDto requestDto) {
        Mascota mascotaExistente = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));

        mascotaExistente.setNombre(requestDto.getNombre());
        mascotaExistente.setEdad(requestDto.getEdad());
        mascotaExistente.setPeso(requestDto.getPeso());
        mascotaExistente.setEspecie(requestDto.getEspecie());
        mascotaExistente.setRaza(requestDto.getRaza());

        return mascotaRepository.save(mascotaExistente);
    }

    public List<Mascota> listarMascotasPorUsuario(Long idUsuario) {
        if(!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con id: " + idUsuario);
        }
        return mascotaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public void eliminarMascota(Long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Mascota no encontrada con id: " + id);
        }
    }

}
