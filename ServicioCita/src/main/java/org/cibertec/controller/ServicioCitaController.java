package org.cibertec.controller;

import java.util.Date;
import java.util.Objects;

import org.cibertec.dto.DetalleDto;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.service.ServicioCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cita")
@CrossOrigin(origins = "*")
public class ServicioCitaController {
	
	@Autowired
	private ServicioCitaService serCitServ;
	
	@GetMapping("/nuevo")
	public ResponseEntity<DetalleDto> nuevaCita(@RequestHeader(name = "Authorization", required = false) String token,
			@RequestParam Long idUsuario, @RequestParam Integer idServicio,@RequestParam Long idMascota,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaCita) {
		
		if (Objects.isNull(idUsuario) || Objects.isNull(idServicio) || Objects.isNull(fechaCita)) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
		ResponseEntity<ServicioResponseDTO> servicioResponse = serCitServ.buscarServicioPorId(idServicio);
		if (!servicioResponse.getStatusCode().is2xxSuccessful() || servicioResponse.getBody() == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	    }
		ServicioResponseDTO servicio = servicioResponse.getBody();
		
		if (!serCitServ.validarDisponibilidad(idServicio, fechaCita)) {
	        return new ResponseEntity<>(HttpStatus.CONFLICT);
	    }
		
		DetalleDto detalle = new DetalleDto();
	    detalle.setNombre(servicio.getNombre());
	    detalle.setPrecio(servicio.getPrecio());
	    detalle.setTotal(servicio.getPrecio());
	    detalle.setCantidad(1);
	    
	    detalle.setFechaCita(fechaCita);
	    detalle.setIdServicio(idServicio);
	    detalle.setIdMascota(idMascota);
	    detalle.setIdUsuario(idUsuario);
		
		return ResponseEntity.ok(detalle);
	}
	
}
