package org.cibertec.controller;

import org.cibertec.dto.ProductoRequestDTO;
import org.cibertec.dto.ProductoResponseDTO;
import org.cibertec.entity.Producto;
import org.cibertec.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/guardar")
    private ResponseEntity<ProductoResponseDTO> guardarProducto(@RequestBody ProductoRequestDTO productoRequestDTO,
                                                                @RequestParam MultipartFile imagen) {
        return productoService.guardar(productoRequestDTO, imagen);
    }

    @GetMapping("/listar")
    private ResponseEntity<List<ProductoResponseDTO>> obtenerProductos() {
        return productoService.obtenerProductos();
    }

    @GetMapping(value = "/buscar", params = "id")
    private ResponseEntity<ProductoResponseDTO> obtenerProductoPorID(@RequestParam int id) {
        return productoService.buscarProductoPorID(id);
    }

    @GetMapping(value = "/buscar", params = "nombre")
    private ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorNombre(@RequestParam String nombre) {
        return productoService.buscarProductoPorNombre(nombre);
    }

    @GetMapping(value = "/buscar", params = "veterinario")
    private ResponseEntity<List<ProductoResponseDTO>> obtenerProductosPorVeterinario(@RequestParam Long veterinario) {
        return productoService.obtenerProductosPorVeterinario(veterinario);
    }

    @PutMapping("/actualizar")
    private String actualizarProductoPorId(@RequestParam int id, @RequestBody Producto producto) {
        return productoService.actualizarProductoPorId(id, producto);
    }

    @DeleteMapping("/eliminar")
    private void eliminarProductoPorId(@RequestParam int id) {
        productoService.eliminarProductoPorId(id);
    }

    @GetMapping("/obtenerImagen/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable int id) {
        return productoService.obtenerImagen(id);
    }
}
