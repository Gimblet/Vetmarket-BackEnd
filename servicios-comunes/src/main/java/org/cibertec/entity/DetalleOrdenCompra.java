package org.cibertec.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DetalleOrdenCompra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetalleOrden;
	private String nombreProducto;
	private double precio,total;
	private int cantidad;
	
	@ManyToOne
	private Orden orden;
	
	@ManyToOne
	private Producto producto;
}
