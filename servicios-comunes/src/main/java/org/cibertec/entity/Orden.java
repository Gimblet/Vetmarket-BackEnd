package org.cibertec.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Orden {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer numeroOrden;
	
	private Date fecha;
	private double total, comision;//comision del 15%
	
	@ManyToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "orden")
	private List<DetalleOrdenCompra> detOrden;
	
	@OneToMany(mappedBy = "orden")
	private List<DetalleServicio> detServicio;
}