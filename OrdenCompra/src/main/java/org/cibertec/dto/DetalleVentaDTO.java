package org.cibertec.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DetalleVentaDTO {
	private Integer detalleId,ordenId;
	private Long idMascota, idUsuairo;
	private String tipoItem,nombreItem;
	private Date fecha;
	private double precio,total;
	private int cantidad;
}
