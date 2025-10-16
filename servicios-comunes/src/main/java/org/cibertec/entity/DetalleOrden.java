package org.cibertec.entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_DETALLE", discriminatorType = DiscriminatorType.STRING)
@Data
public class DetalleOrden {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;
	
	private String nombre;
	private double precio,total,comision;
	
	@ManyToOne
    private Orden orden;
}
