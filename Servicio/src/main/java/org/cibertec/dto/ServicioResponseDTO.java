package org.cibertec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cibertec.entity.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicioResponseDTO {

    private Integer idServicio;
    private double precio;
    private String nombre, descripcion;
    private byte[] img;
    // Usuario
    private Long idUsuario;
    private String nombreUsuario;
    private String apellido;
    private String telefono;

}
