package org.cibertec.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.cibertec.dto.ProductoRequestDTO;
import org.cibertec.dto.ProductoResponseDTO;
import org.cibertec.entity.Producto;
import org.cibertec.mapper.ProductoMapper;
import org.cibertec.repository.IProductoRepository;
import org.cibertec.repository.IUsuarioRepository;
import org.cibertec.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final IProductoRepository productoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ProductoMapper productoMapper;

    public ResponseEntity<ApiResponse<ProductoResponseDTO>> guardar(ProductoRequestDTO productoRequestDTO, MultipartFile imagen) {
        if (!usuarioRepository.existsById(productoRequestDTO.getIdUsuario())) {
            ApiResponse<ProductoResponseDTO> response =
                    new ApiResponse<>(false, "No se pudo encontrar el Usuario con ID " + productoRequestDTO.getIdUsuario(), productoMapper.toDTORequested(productoRequestDTO));

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Producto productoEntidad = productoMapper.toEntitySaved(productoRequestDTO);

        try {
            if (imagen == null || imagen.isEmpty())
                productoEntidad.setImg(null);
            else {
                productoEntidad.setImg(imagen.getBytes());
            }
            productoRepository.save(productoEntidad);

        } catch (Exception e) {
            System.out.println("Hubo un error al obtener el archivo de imagen: " + e.getMessage());
            throw new RuntimeException(e);
        }

        ApiResponse<ProductoResponseDTO> response =
                new ApiResponse<>(true, "producto registrado con exito", productoMapper.toDto(productoEntidad));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<ProductoResponseDTO>> buscarProductoPorID(int id) {
        Optional<Producto> productoEntidad = productoRepository.findById(id);
        if (productoEntidad.isEmpty()) {
            ApiResponse<ProductoResponseDTO> response = new ApiResponse<>(false, "No se enconctro el producto con ID: " + id, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<ProductoResponseDTO> response =
                new ApiResponse<>(true, "Producto encontrado con exito", productoMapper.toDto(productoEntidad.get()));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<List<ProductoResponseDTO>>> obtenerProductos() {
        List<ProductoResponseDTO> list = productoRepository.findAll()
                .stream()
                .map(productoMapper::toDto)
                .toList();
        ApiResponse<List<ProductoResponseDTO>> response =
                new ApiResponse<>(true, "Lista de Productos cargada exitosamente", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<ProductoResponseDTO>>> buscarProductoPorNombre(String nombre) {
        Optional<List<Producto>> listaProductos = productoRepository.findAllByNombreIgnoreCaseContaining(nombre);

        if (listaProductos.isEmpty()) {
            ApiResponse<List<ProductoResponseDTO>> response =
                    new ApiResponse<>(false, "No se encontro ningun producto con el nombre: " + nombre, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<List<ProductoResponseDTO>> listaDTO = listaProductos.map(productos -> productos
                .stream()
                .map(productoMapper::toDto)
                .toList());

        ApiResponse<List<ProductoResponseDTO>> response =
                new ApiResponse<>(true, "Lista de productos con el nombre: " + nombre, listaDTO.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<ProductoResponseDTO>>> obtenerProductosPorVeterinario(Long idVeterinario) {
        Optional<List<Producto>> listaProductos = productoRepository.findAllByUsuario_IdUsuario(idVeterinario);

        if (listaProductos.isEmpty()) {
            ApiResponse<List<ProductoResponseDTO>> response =
                    new ApiResponse<>(false, "No se encontro ningun producto para el veterinario con ID : " + idVeterinario, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<List<ProductoResponseDTO>> listaDTO = listaProductos.map(productos -> productos
                .stream()
                .map(productoMapper::toDto)
                .toList());

        ApiResponse<List<ProductoResponseDTO>> response =
                new ApiResponse<>(true, "Lista de productos para el veterinario con ID : " + idVeterinario, listaDTO.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<ProductoResponseDTO>> actualizarProductoPorId(int id,
                                                                                    ProductoRequestDTO producto,
                                                                                    MultipartFile imagen) {
        if (!usuarioRepository.existsById(producto.getIdUsuario())) {
            ApiResponse<ProductoResponseDTO> response =
                    new ApiResponse<>(false, "No se pudo encontrar el Usuario con ID " + producto.getIdUsuario(), productoMapper.toDTORequested(producto));

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


        try {
            Producto productoHelper = productoRepository.findById(id).get();

            Producto productoEntidad = productoMapper.toEntitySaved(producto);
            productoEntidad.setIdProducto(id);

            if (imagen != null)
                productoEntidad.setImg(imagen.getBytes());
            else {
                productoEntidad.setImg(productoHelper.getImg());
            }

            productoRepository.save(productoEntidad);

            ApiResponse<ProductoResponseDTO> response =
                    new ApiResponse<>(true, "producto actualizado con exito", productoMapper.toDto(productoEntidad));

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IOException e) {
            System.out.println("Hubo un error al actualizar el archivo de imagen: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> restarStockProducto(int idProducto,
                                                      int stock) {
        Producto producto = productoRepository.findById(idProducto).get();
        producto.setStock(producto.getStock() - stock);
        if (producto.getStock() < 0) {
            return ResponseEntity.badRequest().body("No hay suficiente stock del producto");
        }
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

    public ResponseEntity<String> obtenerProductosGeneral() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<ProductoResponseDTO> lista = productoRepository.findAll()
                    .stream()
                    .map(productoMapper::toDto)
                    .toList();
            String json = gson.toJson(lista);
            String userHome = System.getProperty("user.home");
            Files.write(Paths.get(userHome, "productos" + LocalDateTime.now() + ".json"), json.getBytes());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}