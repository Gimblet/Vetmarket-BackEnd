package org.cibertec.dto;

import java.util.Date;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DetalleDto {

    @NotNull(message = "Nombre no puede ser nulo")
    @NotBlank(message = "Nombre no puede estar vacio")
    @Size(min = 1, max = 150, message = "Nombre debe tener entre 1 y 150 caracteres")
	private String nombre;

    @DecimalMin(value = "1", message = "Precio debe ser mayor a 0")
    private double precio;

    @DecimalMin(value = "1", message = "Total debe ser mayor a 0")
    private double total;

    @Min(value = 1, message = "Cantidad debe ser mayor a 0")
    private Integer cantidad;

    @Min(value = 1, message = "Producto debe ser mayor a 0")
    private Integer idProducto;

    private Date fechaCita;

    @Min(value = 1, message = "Servicio debe ser mayor a 0")
    private Integer idServicio;

    @Min(value = 1, message = "Mascota debe ser mayor a 0")
    private Long idMascota;

    @Min(value = 1, message = "Usuario debe ser mayor a 0")
    private Long idUsuario;
}
