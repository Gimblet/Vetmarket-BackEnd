package org.cibertec.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.cibertec.application.mapper.MascotaMapper;
import org.cibertec.application.service.IMascotaService;
import org.cibertec.domain.repository.IMascotaRepository;
import org.cibertec.entity.Mascota;
import org.cibertec.web.dto.MascotaRequestDto;
import org.cibertec.web.dto.MascotaResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements IMascotaService {
    private final IMascotaRepository mascotaRepository;
    private final MascotaMapper mascotaMapper;


    @Override
    public List<MascotaResponseDto> listarMascotas() {
        return mascotaRepository.findAll().stream()
                .map(mascotaMapper::toDto)
                .toList();
    }

    @Override
    public MascotaResponseDto buscarMascotaPorId(Long id) {
        return mascotaRepository.findById(id)
                .map(mascotaMapper::toDto)
                .orElseThrow(() -> new RuntimeException("No se encontro la mascota con id: " + id));
    }

    @Override
    public MascotaResponseDto guardarMascota(MascotaRequestDto requestDto) {
        Mascota mascota = mascotaMapper.toEntity(requestDto);
        return mascotaMapper.toDto(mascotaRepository.save(mascota));
    }

    @Override
    public MascotaResponseDto actualizarMascota(Long id, MascotaRequestDto requestDto) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la mascota con id: " + id));

        mascota.setNombre(requestDto.getNombre());
        mascota.setEdad(requestDto.getEdad());
        mascota.setPeso(requestDto.getPeso());
        mascota.setEspecie(requestDto.getEspecie());
        mascota.setRaza(requestDto.getRaza());
        return mascotaMapper.toDto(mascotaRepository.save(mascota));
    }

    @Override
    public void eliminarMascota(Long id) {
        if(!mascotaRepository.existsById(id)) {
            throw new RuntimeException("No se encontro la mascota con id: " + id);
        }
        mascotaRepository.deleteById(id);
    }
}
