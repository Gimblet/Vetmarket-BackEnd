package org.cibertec.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    @DecimalMin(value = "1", message = "Precio debe ser mayor a 0")
    private double precio;

    @Min(value = 1, message = "Stock debe ser mayor a 0")
    private int stock;

    @NotNull(message = "Nombre no puede ser nulo")
    @NotBlank(message = "Nombre no puede estar vacio")
    @Size(min = 1, max = 150, message = "Nombre debe tener entre 1 y 150 caracteres")
    private String nombre;

    @NotNull(message = "Descripcion no puede ser nulo")
    @NotBlank(message = "Descripcion no puede estar vacio")
    @Size(min = 10, max = 255, message = "Descripcion debe tener entre 10 y 255 caracteres")
    private String descripcion;

    @Min(value = 1, message = "Debe seleccionar un usuario")
    private Long idUsuario;
}
