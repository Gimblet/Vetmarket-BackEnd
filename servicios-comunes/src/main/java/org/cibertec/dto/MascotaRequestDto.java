package org.cibertec.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MascotaRequestDto {
    @NotNull(message = "{mascota.nombre.null}")
    @NotBlank(message = "{mascota.nombre.blank}")
    private String nombre;
    @NotNull(message = "{mascota.edad.null}")
    @Min(value = 0, message = "{mascota.edad.min}")
    private Integer edad;
    @NotNull(message = "{mascota.peso.null}")
    @DecimalMin(value = "0.1", message = "{mascota.peso.decimalMin}")
    private Double peso;
    @NotBlank(message = "{mascota.especie.blank}")
    private String especie;
    @NotBlank(message = "{mascota.raza.blank}")
    private String raza;
    @NotNull(message = "{mascota.usuario.null}")
    private Long idUsuario;
}
