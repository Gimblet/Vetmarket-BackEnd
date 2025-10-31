package org.cibertec.dto;

import java.util.Date;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DetalleVentaDTO {
	@Min(value = 1, message = "ID debe ser mayor a 0")
	private Integer detalleId,ordenId;

	@Min(value = 1, message = "ID debe ser mayor a 0")
	private Long idMascota, idUsuairo;

	@NotNull(message = "Nombre no puede ser nulo")
	@NotBlank(message = "Nombre no puede estar vacio")
	@Size(min = 1, max = 150, message = "Nombre debe tener entre 1 y 150 caracteres")
	private String tipoItem,nombreItem;
	private Date fecha;

	@DecimalMin(value = "1", message = "Precio/total debe ser mayor a 0")
	private double precio,total;

	@Min(value = 1, message = "Cantidad debe ser mayor a 0")
	private int cantidad;
}
