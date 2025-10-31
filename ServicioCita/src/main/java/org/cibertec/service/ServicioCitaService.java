package org.cibertec.service;

import java.util.Date;

import org.cibertec.client.ServicioClient;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.repository.ServicioCitaRepository;
import org.cibertec.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServicioCitaService {

    @Autowired
    private ServicioCitaRepository servCitRep;

    @Autowired
    private ServicioClient serCli;

    // ✅ Validar disponibilidad
    public boolean validarDisponibilidad(Integer idServicio, Date fechaCita) {
        Integer count = servCitRep.contarCitaPorServicioYFecha(idServicio, fechaCita);
        return count < 5;
    }

    // ✅ Buscar servicio por ID con manejo de errores
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> buscarServicioPorId(Integer idServicio) {
        try {
            ResponseEntity<ServicioResponseDTO> response = serCli.buscarServicioPorId(idServicio);

            if (response.getBody() == null) {
                ApiResponse<ServicioResponseDTO> apiResponse =
                        new ApiResponse<>(false, "Servicio no encontrado con ID " + idServicio, null);
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }

            ApiResponse<ServicioResponseDTO> apiResponse =
                    new ApiResponse<>(true, "Servicio encontrado correctamente", response.getBody());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception ex) {
            return fallbackBuscarServicioPorId(idServicio, ex);
        }
    }

    // ✅ Fallback en caso de error
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> fallbackBuscarServicioPorId(Integer idServicio, Throwable ex) {
        System.err.println("⚠️ Fallback en buscarServicioPorId(idServicio=" + idServicio + "): " + ex.getMessage());

        ServicioResponseDTO respuesta = new ServicioResponseDTO();
        respuesta.setIdServicio(idServicio);
        respuesta.setNombre("Servicio no disponible temporalmente");
        respuesta.setDescripcion("Error al obtener el servicio: " + ex.getMessage());
        respuesta.setPrecio(0.0);

        ApiResponse<ServicioResponseDTO> apiResponse =
                new ApiResponse<>(false, "Error al comunicarse con el servicio externo", respuesta);
        return new ResponseEntity<>(apiResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
}