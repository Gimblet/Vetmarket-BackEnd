package org.cibertec.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MascotaResponseDto {
    private Long idMascota;
    private String nombre;
    private Integer edad;
    private Double peso;
    private String especie;
    private String raza;
    // Usuario
    private Long idUsuario;
    private String nombreUsuario;
    private String apellido;
    private String telefono;
}
