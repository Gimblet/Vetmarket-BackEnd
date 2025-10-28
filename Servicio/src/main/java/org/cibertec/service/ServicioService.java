package org.cibertec.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.cibertec.dto.ServicioRequestDTO;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.entity.Servicio;
import org.cibertec.entity.Usuario;
import org.cibertec.mapper.ServicioMapper;
import org.cibertec.repository.IServicioRepository;
import org.cibertec.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {
    private final IServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;
    private final IUsuarioRepository usuarioRepository;
private final KafkaPublisherService kafkaPublisherService;
// Listar Servicios
@CircuitBreaker(name = "servicioService", fallbackMethod = "fallbackListarServicio")
@Retry(name="servicioService")
    public List<ServicioResponseDTO> listarServicio() {
        return servicioRepository.findAll().stream()
                .map(servicioMapper::toDto)
                .toList();
    }
// Buscar por Id
@CircuitBreaker(name = "servicioService", fallbackMethod = "fallbackBuscarServicioPorId")
    @Retry(name="servicioService")
        public ServicioResponseDTO buscarServicioPorId(Integer id) {
            return servicioRepository.findById(id)
                    .map(servicioMapper::toDto)
                    .orElseThrow(() -> new RuntimeException("No se encontro el servicio con id: " + id));
        }

// Crear Servicio
@CircuitBreaker(name = "servicioService", fallbackMethod = "fallbackCrearServicio")
@Retry(name="servicioService")
    public ServicioResponseDTO crearServicio(
            String nombre,
            String descripcion,
            Double precio,
            Long usuarioId,
            MultipartFile img) throws IOException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se encontro el usuario con id: " + usuarioId));

        ServicioRequestDTO request = ServicioRequestDTO.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .idUsuario(usuarioId)
                .build();

        byte[] imgBytes = img != null ? img.getBytes() : null;
        Servicio servicio = servicioMapper.toEntity(request,imgBytes ,usuario);
        Servicio guardado = servicioRepository.save(servicio);
        kafkaPublisherService.enviarMensageKafka(request);
        return servicioMapper.toDto(guardado);
    }

    // Actualizar Servicio
    @CircuitBreaker(name = "servicioService", fallbackMethod = "fallbackActualizarServicio")
    @Retry(name="servicioService")
    public ServicioResponseDTO actualizarServicio(
            Integer id,
            String nombre,
            String descripcion,
            Double precio,
            MultipartFile img) throws IOException {

        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // Actualizar solo los campos que no son null
        if (nombre != null) servicio.setNombre(nombre);
        if (descripcion != null) servicio.setDescripcion(descripcion);
        if (precio != null) servicio.setPrecio(precio);
        if (img != null && !img.isEmpty()) {
            servicio.setImg(img.getBytes());
        }

        Servicio actualizado = servicioRepository.save(servicio);
        return servicioMapper.toDto(actualizado);
    }
// Eliminar Servicio
@CircuitBreaker(name = "servicioService", fallbackMethod = "fallbackEliminarServicio")
@Retry(name="servicioService")
        public void eliminarServicio(Integer id) {
            if(!servicioRepository.existsById(id)) {
                throw new RuntimeException("No se encontro el servicio con id: " + id);
            }
            servicioRepository.deleteById(id);
        }
    // ModifRepas
        public byte[] obtenerImagen(Integer id) {
        return servicioRepository.findById(id)
                .map(Servicio::getImg)
                .orElse(null);
        }
// Listar Servicio por Veterinario
@CircuitBreaker(name = "servicioService", fallbackMethod = "fallbackListarServicioPorVeterinario")
@Retry(name="servicioService")
    public List<ServicioResponseDTO> listarServiciosPorVeterinario(Long usuarioId) {
        return servicioRepository.findByUsuarioIdUsuario(usuarioId).stream()
                .map(servicioMapper::toDto)
                .toList();
    }

    /*/*****************************/
    /********** FallBacks **********/
    /*/*****************************/
//Listar
    public List<Servicio> fallbackListarServicio(Throwable ex) {
        System.err.println("Fallback en listar servicio : " + ex.getMessage());
        return List.of();
    }
//BuscarPorId
    public Servicio fallbackBuscarServicioPorId(Long id, Throwable ex) {
        System.err.println("Fallback en buscar servicio por id="+id + ": "+ ex.getMessage());
        return new Servicio();
    }
// Crear
    public Servicio fallbackCrearServicio(Servicio servicio, Throwable ex) throws Exception {
        System.err.println("Fallback en crear servicio: " + ex.getMessage());
        throw new Exception();
    }
// Actualizar
    public Usuario fallbackActualizarServicio(Servicio servicio, Throwable ex) throws Exception {
        System.err.println("Fallback actualizar servicio: " + ex.getMessage());
        throw new Exception();
    }
// Eliminar
    public String fallbackEliminarServicio(Long id, Throwable ex) {

        System.err.println("Fallback eliminar servicio con id=" + id + ": " + ex.getMessage());
        return "Error";
    }
    // Listar Servicio por Veterinario
    public List<Servicio> fallbackListarServicioPorVeterinario(Long usuarioId, Throwable ex) {
        System.err.println("Fallback en buscar usuarios por rol id="+ usuarioId + ": "  + ex.getMessage());
        return List.of();
    }

    }

