package org.cibertec.controller;

import org.cibertec.entity.Producto;
import org.cibertec.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/guardar")
    private Producto guardarProducto(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @GetMapping("/listar")
    private List<Producto> obtenerProductos() {
        return productoService.obtenerProductos();
    }

    @GetMapping(value = "/buscar", params = "id")
    private Optional<Producto> obtenerProductoPorID(@RequestParam int id) {
        return productoService.buscarProductoPorID(id);
    }

    @GetMapping(value = "/buscar", params = "nombre")
    private Optional<List<Producto>> obtenerProductoPorNombre(@RequestParam String nombre) {
        return productoService.buscarProductoPorNombre(nombre);
    }

    @GetMapping(value = "/buscar", params = "veterinario")
    private Optional<List<Producto>> obtenerProductosPorVeterinario(@RequestParam Long idUsuario) {
        return productoService.obtenerProductosPorVeterinario(idUsuario);
    }

    @PutMapping("/actualizar")
    private String actualizarProductoPorId(@RequestParam int id, @RequestBody Producto producto) {
        return productoService.actualizarProductoPorId(id, producto);
    }

    @DeleteMapping("/eliminar")
    private void eliminarProductoPorId(@RequestParam int id) {
        productoService.eliminarProductoPorId(id);
    }
}
