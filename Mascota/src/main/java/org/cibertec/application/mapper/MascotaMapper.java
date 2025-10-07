package org.cibertec.application.mapper;

import org.cibertec.entity.Mascota;
import org.cibertec.entity.Usuario;
import org.cibertec.web.dto.MascotaRequestDto;
import org.cibertec.web.dto.MascotaResponseDto;

public interface MascotaMapper {
    MascotaResponseDto toDto(Mascota mascota);
    Mascota toEntity(MascotaRequestDto mascotaRequestDto, Usuario usuario);
}
