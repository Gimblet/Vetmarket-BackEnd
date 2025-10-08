package org.cibertec.application.mapper.impl;

import org.cibertec.application.mapper.MascotaMapper;
import org.cibertec.entity.Mascota;
import org.cibertec.web.dto.MascotaRequestDto;
import org.cibertec.web.dto.MascotaResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MascotaMappeImpl implements MascotaMapper {
    @Override
    public MascotaResponseDto toDto(Mascota mascota) {
        return MascotaResponseDto.builder()
                .idMascota(mascota.getIdMascota())
                .nombre(mascota.getNombre())
                .edad(mascota.getEdad())
                .peso(mascota.getPeso())
                .especie(mascota.getEspecie())
                .raza(mascota.getRaza())
                // TODO: Descomentar cuando se conecte con el servicio Usuario mediante FeignClient
                /*
                .idUsuario(mascota.getUsuario().getIdUsuario())
                .nombreUsuario(mascota.getUsuario().getNombre())
                .apellido(mascota.getUsuario().getApellido())
                .telefono(mascota.getUsuario().getTelefono())
                */
                .build();

    }

    @Override
    public Mascota toEntity(MascotaRequestDto mascotaRequestDto) {
        return Mascota.builder()
                .nombre(mascotaRequestDto.getNombre())
                .edad(mascotaRequestDto.getEdad())
                .peso(mascotaRequestDto.getPeso())
                .especie(mascotaRequestDto.getEspecie())
                .raza(mascotaRequestDto.getRaza())
                // .usuario(usuario)
                .build();
    }
}
