package org.cibertec.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;
    private String apellido;

    private String numeroDocumento;
    private String telefono;
    private String direccion;
    private String correo;
    private String ruc;//sera null para clientes y obligatorio para veterinarios

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private Rol rol;
    
    //Relacionar con la tabla Producto
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Producto> producto;
    
    //Relacionar con la tabla Orden
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Orden> orden;
    
    //Relacionar con la tabla Servicio
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Servicio> servicio;
}

