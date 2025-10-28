package org.cibertec.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
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

    @CircuitBreaker(name = "mascotaService", fallbackMethod = "fallbackListarMascotas")
    @Retry(name = "mascotaService")
    public List<Mascota> listarMascotas() {
        return mascotaRepository.findAll();
    }

    @CircuitBreaker(name = "mascotaService", fallbackMethod = "fallbackBuscarMascotaPotId")
    @Retry(name = "mascotaService")
    public Optional<Mascota> buscarMascotaPorId(Long id) {
        return mascotaRepository.findById(id);
    }

    @CircuitBreaker(name = "mascotaService", fallbackMethod = "fallbackCrearMascota")
    @Retry(name = "mascotaService")
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

    @CircuitBreaker(name = "mascotaService", fallbackMethod = "fallbackActualizarMascotas")
    @Retry(name = "mascotaService")
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

    @CircuitBreaker(name = "mascotaService", fallbackMethod = "fallbackListarMascotasPorUsuario")
    @Retry(name = "mascotaService")
    public List<Mascota> listarMascotasPorUsuario(Long idUsuario) {
        if(!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con id: " + idUsuario);
        }
        return mascotaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @CircuitBreaker(name = "mascotaService", fallbackMethod = "fallbackEliminarMascota")
    @Retry(name = "mascotaService")
    public void eliminarMascota(Long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Mascota no encontrada con id: " + id);
        }
    }

    /*/*****************************/
    /********** FallBacks **********/
    /*/*****************************/

    public List<Mascota> fallbackListarMascotas(Throwable ex) {
        Mascota mascota = new Mascota();
        mascota.setIdMascota(0L);
        mascota.setNombre("ERROR");
        mascota.setEdad(0);
        mascota.setPeso(0.0);
        mascota.setEspecie("No disponible");
        mascota.setRaza("No disponible");

        System.out.println("FALLBACK: Error al listar mascotas: " + ex.getMessage());
        return List.of(mascota);
    }

    public Optional<Mascota> fallbackBuscarMascotaPorId(Long id, Throwable ex) {
        Mascota mascotaError = new Mascota();
        mascotaError.setIdMascota(0L);
        mascotaError.setNombre("ERROR");
        mascotaError.setEdad(0);
        mascotaError.setPeso(0.0);
        mascotaError.setEspecie("No disponible");
        mascotaError.setRaza("No disponible");

        System.out.println("FALLBACK: Error al buscar mascota por ID " + id + ": " + ex.getMessage());
        return Optional.of(mascotaError);
    }

    public Mascota fallbackCrearMascota(MascotaRequestDto requestDto, Throwable ex) {
        Mascota mascotaError = new Mascota();
        mascotaError.setIdMascota(0L);
        mascotaError.setNombre("ERROR - Creación fallida");
        mascotaError.setEdad(0);
        mascotaError.setPeso(0.0);
        mascotaError.setEspecie("No disponible");
        mascotaError.setRaza("No disponible");

        System.out.println("FALLBACK: Error al crear mascota: " + ex.getMessage());
        return mascotaError;
    }

    public Mascota fallbackActualizarMascotas(Long id, MascotaRequestDto requestDto, Throwable ex) {
        Mascota mascotaError = new Mascota();
        mascotaError.setIdMascota(id);
        mascotaError.setNombre("ERROR - Actualización fallida");
        mascotaError.setEdad(0);
        mascotaError.setPeso(0.0);
        mascotaError.setEspecie("No disponible");
        mascotaError.setRaza("No disponible");

        System.out.println("FALLBACK: Error al actualizar mascota con ID " + id + ": " + ex.getMessage());
        return mascotaError;
    }

    public List<Mascota> fallbackListarMascotasPorUsuario(Long idUsuario, Throwable ex) {
        Mascota mascotaError = new Mascota();
        mascotaError.setIdMascota(0L);
        mascotaError.setNombre("ERROR");
        mascotaError.setEdad(0);
        mascotaError.setPeso(0.0);
        mascotaError.setEspecie("No disponible");
        mascotaError.setRaza("No disponible");

        System.out.println("FALLBACK: Error al listar mascotas del usuario " + idUsuario + ": " + ex.getMessage());
        return List.of(mascotaError);
    }

    public void fallbackEliminarMascota(Long id, Throwable ex) {
        System.out.println("FALLBACK: Error al eliminar mascota con ID " + id + ": " + ex.getMessage());
    }
}
