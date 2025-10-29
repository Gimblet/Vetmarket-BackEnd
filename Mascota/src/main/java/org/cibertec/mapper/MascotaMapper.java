package org.cibertec.mapper;

import org.cibertec.dto.MascotaRequestDto;
import org.cibertec.dto.MascotaResponseDto;
import org.cibertec.entity.Mascota;
import org.cibertec.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MascotaMapper {

    public MascotaResponseDto toDto(Mascota mascota) {
        return MascotaResponseDto.builder()
                .idMascota(mascota.getIdMascota())
                .nombre(mascota.getNombre())
                .edad(mascota.getEdad())
                .peso(mascota.getPeso())
                .especie(mascota.getEspecie())
                .raza(mascota.getRaza())
                .idUsuario(mascota.getUsuario().getIdUsuario())
                .ruc(mascota.getUsuario().getRuc())
                .username(mascota.getUsuario().getUsername())
                .correo(mascota.getUsuario().getCorreo())
                .telefono(mascota.getUsuario().getTelefono())
                .direccion(mascota.getUsuario().getDireccion())
                .build();
    }

    public Mascota toEntity(MascotaRequestDto mascotaRequestDto, Usuario usuario) {
        return Mascota.builder()
                .nombre(mascotaRequestDto.getNombre())
                .edad(mascotaRequestDto.getEdad())
                .peso(mascotaRequestDto.getPeso())
                .especie(mascotaRequestDto.getEspecie())
                .raza(mascotaRequestDto.getRaza())
                .usuario(usuario)
                .build();
    }
}
