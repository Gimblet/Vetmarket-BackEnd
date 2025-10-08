package org.cibertec.application.service;

import org.cibertec.web.dto.MascotaRequestDto;
import org.cibertec.web.dto.MascotaResponseDto;

import java.util.List;

public interface IMascotaService {
    List<MascotaResponseDto> listarMascotas();
    MascotaResponseDto buscarMascotaPorId(Long id);
    MascotaResponseDto guardarMascota(MascotaRequestDto requestDto);
    MascotaResponseDto actualizarMascota(Long id,MascotaRequestDto requestDto);
    void eliminarMascota(Long id);
}
