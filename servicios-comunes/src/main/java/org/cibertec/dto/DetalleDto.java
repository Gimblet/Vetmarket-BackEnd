package org.cibertec.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DetalleDto {
	
	private String nombre;
    private double precio;
    private double total;
    
    private Integer cantidad;
    private Integer idProducto;
    
    private Date fechaCita;
    private Integer idServicio;
    private Long idMascota;
    
    private Long idUsuario;
}
