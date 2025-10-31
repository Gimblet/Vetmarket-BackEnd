package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.client.ProductoClient;
import org.cibertec.dto.ProductoResponseDTO;
import org.cibertec.entity.CarritoCompra;
import org.cibertec.repository.CarritoRepository;
import org.cibertec.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoService {
	@Autowired
    private CarritoRepository cRep;
	
    @Autowired
    private ProductoClient pCli;
    
    public ResponseEntity<ApiResponse<List<CarritoCompra>>> obtenerCarritoPorUsuario(Long idUsuario) {
        List<CarritoCompra> compraList = cRep.findByIdUsuario(idUsuario);

        if (compraList.isEmpty()) {
            ApiResponse<List<CarritoCompra>> response =
                    new ApiResponse<>(false, "No se encontro ningun producto/servicio para el cliente con ID : " + idUsuario, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<List<CarritoCompra>> response =
                new ApiResponse<>(true, "Lista de carrito para el cliente con ID : " + idUsuario, compraList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<CarritoCompra>> agregarProducto(Long idUsuario, Integer idProducto, Integer cantidad) {
    	Optional<CarritoCompra> existente = cRep.findByIdUsuarioAndIdProducto(idUsuario, idProducto);
        if (existente.isPresent()) {
            throw new RuntimeException("El producto ya existe en el carrito");
        }
    	
        ResponseEntity<ApiResponse<ProductoResponseDTO>> response = pCli.obtenerProductoPorID(idProducto);
        ProductoResponseDTO producto = response.getBody().getData();

        if (producto == null || producto.getNombre().contains("no disponible")) {
            throw new RuntimeException("Producto no disponible o servicio de productos caído");
        }

        CarritoCompra carrito = new CarritoCompra();
        carrito.setIdUsuario(idUsuario);
        carrito.setIdProducto(idProducto);
        carrito.setNombreProducto(producto.getNombre());
        carrito.setDescripcion(producto.getDescripcion());
        carrito.setPrecio(producto.getPrecio());
        carrito.setCantidad(cantidad);
        carrito.setTotal(producto.getPrecio() * cantidad);

        CarritoCompra carritoGuardado = cRep.save(carrito);

        ApiResponse<CarritoCompra> respuesta =
                new ApiResponse<>(true, "Carrito agregado con exito", carritoGuardado);

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

    }
    
    @Transactional
    public void eliminarProducto(Long idUsuario, Integer idProducto) {
        cRep.deleteByIdUsuarioAndIdProducto(idUsuario, idProducto);
    }
}
