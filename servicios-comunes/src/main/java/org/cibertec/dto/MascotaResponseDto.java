package org.cibertec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MascotaResponseDto {
    private Long idMascota;
    private String nombre;
    private Integer edad;
    private Double peso;
    private String especie;
    private String raza;
    //Usuario
    private Long idUsuario;
    private String ruc;
    private String username;
    private String correo;
    private String telefono;
    private String direccion;
}
