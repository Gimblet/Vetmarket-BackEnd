package org.cibertec.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MascotaRequestDto {
    private String nombre;
    private Integer edad;
    private Double peso;
    private String especie;
    private String raza;
    private Long idUsuario;
}
