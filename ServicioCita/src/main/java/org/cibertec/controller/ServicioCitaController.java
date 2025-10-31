package org.cibertec.controller;

import java.util.Date;
import java.util.Objects;

import org.cibertec.dto.DetalleDto;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.service.ServicioCitaService;
import org.cibertec.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cita")
public class ServicioCitaController {

    @Autowired
    private ServicioCitaService serCitServ;

    @GetMapping("/nuevo")
    public ResponseEntity<ApiResponse<DetalleDto>> nuevaCita(
            @RequestHeader(name = "Authorization", required = false) String token,
            @RequestParam Long idUsuario,
            @RequestParam Integer idServicio,
            @RequestParam Long idMascota,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaCita) {

        // Validaciones básicas
        if (Objects.isNull(idUsuario) || Objects.isNull(idServicio) || Objects.isNull(fechaCita)) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Parámetros incompletos", null), HttpStatus.BAD_REQUEST);
        }

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Token inválido o no proporcionado", null), HttpStatus.UNAUTHORIZED);
        }

        // Buscar servicio
        ResponseEntity<ApiResponse<ServicioResponseDTO>> servicioResponse = serCitServ.buscarServicioPorId(idServicio);
        ApiResponse<ServicioResponseDTO> servicioApiResponse = servicioResponse.getBody();

        if (servicioApiResponse == null || !servicioApiResponse.isSuccess()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, "No se pudo obtener el servicio o no está disponible", null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }

        ServicioResponseDTO servicio = servicioApiResponse.getData();

        // Validar disponibilidad
        if (!serCitServ.validarDisponibilidad(idServicio, fechaCita)) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, "No hay disponibilidad para la fecha seleccionada", null),
                    HttpStatus.CONFLICT
            );
        }

        // Crear detalle
        DetalleDto detalle = new DetalleDto();
        detalle.setNombre(servicio.getNombre());
        detalle.setPrecio(servicio.getPrecio());
        detalle.setTotal(servicio.getPrecio());
        detalle.setCantidad(1);
        detalle.setFechaCita(fechaCita);
        detalle.setIdServicio(idServicio);
        detalle.setIdMascota(idMascota);
        detalle.setIdUsuario(idUsuario);

        ApiResponse<DetalleDto> response =
                new ApiResponse<>(true, "Cita creada exitosamente", detalle);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}