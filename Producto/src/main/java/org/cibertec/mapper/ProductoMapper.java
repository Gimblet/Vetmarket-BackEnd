package org.cibertec.mapper;

import org.cibertec.dto.ProductoRequestDTO;
import org.cibertec.dto.ProductoResponseDTO;
import org.cibertec.entity.Producto;
import org.cibertec.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public ProductoResponseDTO toDto(Producto producto) {
        return ProductoResponseDTO.builder()
                .IdProducto(producto.getIdProducto())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .idUsuario(producto.getUsuario().getIdUsuario())
                .ruc(producto.getUsuario().getRuc())
                .username(producto.getUsuario().getUsername())
                .correo(producto.getUsuario().getCorreo())
                .telefono(producto.getUsuario().getTelefono())
                .direccion(producto.getUsuario().getDireccion())
                .build();
    }

    public Producto toEntity(ProductoRequestDTO productoRequestDTO, Usuario usuario) {
        return Producto.builder()
                .nombre(productoRequestDTO.getNombre())
                .stock(productoRequestDTO.getStock())
                .precio(productoRequestDTO.getPrecio())
                .descripcion(productoRequestDTO.getDescripcion())
                .usuario(usuario)
                .build();
    }

    public Producto toEntitySaved(ProductoRequestDTO productoRequestDTO) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(productoRequestDTO.getIdUsuario());
        return Producto.builder()
                .nombre(productoRequestDTO.getNombre())
                .stock(productoRequestDTO.getStock())
                .precio(productoRequestDTO.getPrecio())
                .descripcion(productoRequestDTO.getDescripcion())
                .usuario(usuario)
                .build();
    }
}
