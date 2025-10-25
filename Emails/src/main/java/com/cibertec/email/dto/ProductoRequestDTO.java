package com.cibertec.email.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    private double precio;
    private int stock;
    private String nombre;
    private String descripcion;
    private Long idUsuario;
}
