package org.cibertec.service;

import java.util.Date;

import org.cibertec.client.ServicioClient;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.repository.ServicioCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ServicioCitaService {
	@Autowired
	private ServicioCitaRepository servCitRep;
	
    @Autowired
    private ServicioClient serCli;
	
	public boolean validarDisponibilidad(Integer idServicio, Date fechaCita) {
        Integer count = servCitRep.contarCitaPorServicioYFecha(idServicio, fechaCita);
        return count < 5;
    }
	
    
    @CircuitBreaker(name = "servicioCitaService", fallbackMethod = "fallbackBuscarServicioPorId")
    @Retry(name = "servicioCitaService")
	public ResponseEntity<ServicioResponseDTO> buscarServicioPorId(Integer idServicio) {
        return serCli.buscarServicioPorId(idServicio);
        
    }


    public ResponseEntity<ServicioResponseDTO> fallbackBuscarServicioPorId(Integer idServicio, Throwable ex) {
        System.err.println("Fallback en buscarServicioPorId(idServicio=" + idServicio + "): " + ex.getMessage());

        ServicioResponseDTO respuesta = new ServicioResponseDTO();
        respuesta.setIdServicio(idServicio);
        respuesta.setNombre("Servicio no disponible temporalmente");
        respuesta.setDescripcion("Error al obtener el servicio: " + ex.getMessage());
        respuesta.setPrecio(0.0);

        return ResponseEntity.ok(respuesta);
    }

}
