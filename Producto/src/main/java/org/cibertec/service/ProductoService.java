package org.cibertec.service;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.ProductoRequestDTO;
import org.cibertec.dto.ProductoResponseDTO;
import org.cibertec.entity.Producto;
import org.cibertec.entity.Usuario;
import org.cibertec.mapper.ProductoMapper;
import org.cibertec.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    @Autowired
    private IProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    public ResponseEntity<ProductoResponseDTO> guardar(ProductoRequestDTO productoRequestDTO, MultipartFile imagen) {
//       TODO: Complete when usuario entity is pushed
//        if (!usuarioServiceClient.existsById) {
//            return ResponseEntity.notFound().build();
//        }

//        TODO: Modify new Usuario when the actual Usuario entity is added
//        Usuario usuario = UsuarioClient.getUsuarioByID(productoRequestDTO.getIdUsuario());

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(Long.parseLong("1"));
        usuario.setNombre("nombre prueba9324524");
        usuario.setApellido("apellido prueba");
        usuario.setCorreo("correo_prubea@gmail.com");
        usuario.setDireccion("123 calle manzana");
        usuario.setTelefono("12345678");
        usuario.setPassword("12345678");
        usuario.setRuc("12345678");
        usuario.setTelefono("99945678");
        usuario.setUsername("vetfarma");

        Producto productoEntidad = productoMapper.toEntity(productoRequestDTO, usuario);

        try {
            if (imagen == null || imagen.isEmpty())
                throw new IOException();
//        productoEntidad.setUsuario(usuarioServiceClient.findById(productoRequestDTO.getIdUsuario()));
            productoEntidad.setImg(imagen.getBytes());
            productoRepository.save(productoEntidad);
        } catch (IOException e) {
            System.out.println("Hubo un error al obtener el archivo de imagen: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(productoMapper.toDto(productoEntidad));
    }

    public ResponseEntity<ProductoResponseDTO> buscarProductoPorID(int id) {
        return ResponseEntity.ok(productoRepository.findById(id)
                .map(productoMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("No existe el producto con el id: " + id)));
    }

    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductos() {
        return ResponseEntity.ok(productoRepository.findAll()
                .stream()
                .map(productoMapper::toDto)
                .toList());
    }

    public ResponseEntity<List<ProductoResponseDTO>> buscarProductoPorNombre(String nombre) {
        Optional<List<Producto>> listaProductos = productoRepository.findAllByNombreIgnoreCaseContaining(nombre);
        return listaProductos.map(productos -> ResponseEntity.ok(productos
                .stream()
                .map(productoMapper::toDto)
                .toList())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosPorVeterinario(Long idVeterinario) {
        Optional<List<Producto>> listaProductos = productoRepository.findAllByUsuario_IdUsuario(idVeterinario);
        return listaProductos.map(productos -> ResponseEntity.ok(productos
                .stream()
                .map(productoMapper::toDto)
                .toList())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> actualizarProductoPorId(int id,
                                                          ProductoRequestDTO producto,
                                                          MultipartFile imagen) {
//         TODO: Implementar cuando Usuario se encuentre listo
//        if (!productoRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//
        try {
            Usuario usuario = new Usuario();
//            usuario.setIdUsuario(Long.parseLong("1"));
//            usuario.setNombre("nombre prueba9324524");
//            usuario.setApellido("apellido prueba");
//            usuario.setCorreo("correo_prubea@gmail.com");
//            usuario.setDireccion("123 calle manzana");
//            usuario.setTelefono("12345678");
//            usuario.setPassword("12345678");
//            usuario.setRuc("12345678");
//            usuario.setTelefono("99945678");
//            usuario.setUsername("vetfarma");

            Producto productoEntidad = productoMapper.toEntity(producto, usuario);
            productoEntidad.setIdProducto(id);
            if (imagen != null)
                productoEntidad.setImg(imagen.getBytes());
            productoRepository.save(productoEntidad);
            return ResponseEntity.ok("Producto con ID : " + productoEntidad.getIdProducto() + "actualizado con exito");

        } catch (IOException e) {
            System.out.println("Hubo un error al actualizar el archivo de imagen: " + e.getMessage());
            throw new RuntimeException(e);
        }
//        return ResponseEntity.ok("FALTA IMPLEMENTAR EL SERVICIO DE ACTUALIZAR PRODUCTO POR ID");
    }

    public ResponseEntity<String> actualizarStockProducto(int idProducto,
                                                          int stock) {
        Producto producto = productoRepository.findById(idProducto).get();
        producto.setStock(stock);
        productoRepository.save(producto);
        return ResponseEntity.ok(
                "Stock del Producto con ID : " +
                producto.getIdProducto() +
                " actualizado con exito");
    }

    public ResponseEntity<String> eliminarProductoPorId(int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.ok("Producto eliminado con exito");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<byte[]> obtenerImagen(int id) {
        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isEmpty() || producto.get().getImg() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().
                    header("Content-Type", "image/jpeg").
                    body(producto.get().getImg());
        }

    }
}
