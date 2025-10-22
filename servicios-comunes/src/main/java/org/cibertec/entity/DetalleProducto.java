package org.cibertec.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@DiscriminatorValue("PRODUCTO")
@Data
public class DetalleProducto extends DetalleOrden{
	private int cantidad;
	
	@ManyToOne
	private Producto producto;
}
