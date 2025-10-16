package org.cibertec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDTO {
	private String nombre;
    private String apellido;
    private String numeroDocumento;
    private String telefono;
    private String direccion;
    private String correo;
    private String ruc;
    private String username;
    private String password;
    private Long idRol;
}
