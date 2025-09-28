package org.cibertec.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DetalleServicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetalleServicio;
	private String nombreServicio;
	private double precio;
	private Date fechaCita;
	
	@ManyToOne
	private Orden orden;
	
	@ManyToOne
	private Servicio servicio;
}
