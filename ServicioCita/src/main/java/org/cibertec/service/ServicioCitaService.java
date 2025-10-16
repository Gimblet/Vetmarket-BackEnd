package org.cibertec.service;

import java.util.Date;

import org.cibertec.client.ServicioClient;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.repository.ServicioCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
	
	public ResponseEntity<ServicioResponseDTO> buscarServicioPorId(Integer idServicio) {
        return serCli.buscarServicioPorId(idServicio);
        
    }

}
