package org.cibertec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicioRequestDTO {

    private double precio;
    private String nombre, descripcion;
    private byte[] imagen;

    // comentado temporalmente
    // private Long idUsuario;

}
