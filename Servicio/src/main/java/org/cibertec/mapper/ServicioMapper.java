package org.cibertec.mapper;

import org.cibertec.dto.ServicioRequestDTO;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.entity.Mascota;
import org.cibertec.entity.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {

    public ServicioResponseDTO toDto(Servicio servicio) {
        return ServicioResponseDTO.builder()
                .idServicio(servicio.getIdServicio())
                .precio(servicio.getPrecio())
                .nombre(servicio.getNombre())
                .descripcion(servicio.getDescripcion())
                // TODO: Descomentar cuando se conecte con el servicio Usuario mediante FeignClient
                /*
                .idUsuario(mascota.getUsuario().getIdUsuario())
                .nombreUsuario(mascota.getUsuario().getNombre())
                .apellido(mascota.getUsuario().getApellido())
                .telefono(mascota.getUsuario().getTelefono())
                */
                .build();
    }

    public Servicio toEntity(ServicioRequestDTO servicioRequestDTO, byte[] img) {
        return Servicio.builder()
                .precio(servicioRequestDTO.getPrecio())
                .nombre(servicioRequestDTO.getNombre())
                .descripcion(servicioRequestDTO.getDescripcion())
                // .usuario(usuario)
                .img(img)
                .build();
    }
}
