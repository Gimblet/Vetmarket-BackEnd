package org.cibertec.service;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.ServicioRequestDTO;
import org.cibertec.dto.ServicioResponseDTO;
import org.cibertec.entity.Mascota;
import org.cibertec.entity.Servicio;
import org.cibertec.mapper.ServicioMapper;
import org.cibertec.repository.IServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {
    private final IServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;


    public List<ServicioResponseDTO> listarServicio() {
        return servicioRepository.findAll().stream()
                .map(servicioMapper::toDto)
                .toList();
    }

        public ServicioResponseDTO buscarServicioPorId(Integer id) {
            return servicioRepository.findById(id)
                    .map(servicioMapper::toDto)
                    .orElseThrow(() -> new RuntimeException("No se encontro el servicio con id: " + id));
        }


        public ServicioResponseDTO guardarServicio(ServicioRequestDTO requestDTO) {
            Servicio servicio = servicioMapper.toEntity(requestDTO);
            return servicioMapper.toDto(servicioRepository.save(servicio));
        }


        public ServicioResponseDTO actualizarServicio(Integer id, ServicioRequestDTO requestDTO) {
            Servicio servicio = servicioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro el servicio con id: " + id));

            servicio.setPrecio(requestDTO.getPrecio());
            servicio.setNombre(requestDTO.getNombre());
            servicio.setDescripcion(requestDTO.getDescripcion());
            servicio.setImg(requestDTO.getImg());
            return servicioMapper.toDto(servicioRepository.save(servicio));

        }


        public void eliminarServicio(Integer id) {
            if(!servicioRepository.existsById(id)) {
                throw new RuntimeException("No se encontro el servicio con id: " + id);
            }
            servicioRepository.deleteById(id);
        }
    }

