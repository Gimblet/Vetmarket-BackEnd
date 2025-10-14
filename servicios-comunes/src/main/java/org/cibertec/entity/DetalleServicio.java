package org.cibertec.entity;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@DiscriminatorValue("Servicio")
@Data
public class DetalleServicio extends DetalleOrden {
	private Date fechaCita;

	@ManyToOne
	private Servicio servicio;
}
