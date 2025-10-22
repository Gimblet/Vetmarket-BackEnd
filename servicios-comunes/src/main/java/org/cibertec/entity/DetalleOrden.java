package org.cibertec.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "TIPO_DETALLE",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DetalleProducto.class, name = "PRODUCTO"),
    @JsonSubTypes.Type(value = DetalleServicio.class, name = "SERVICIO")
})
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
	@JsonBackReference
    private Orden orden;
}
