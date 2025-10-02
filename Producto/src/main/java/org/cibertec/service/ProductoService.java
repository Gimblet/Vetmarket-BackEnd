package org.cibertec.service;

import org.cibertec.entity.Producto;
import org.cibertec.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private IProductoRepository productoRepository;

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> buscarProductoPorID(int id) {
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    public Optional<List<Producto>> buscarProductoPorNombre(String nombre) {
        return productoRepository.findAllByNombreIgnoreCaseContaining(nombre);
    }

    public Optional<List<Producto>> obtenerProductosPorVeterinario(Long idVeterinario) {
        return productoRepository.findAllByUsuario_IdUsuario(idVeterinario);
    }

    public String actualizarProductoPorId(int id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setIdProducto(id);
            productoRepository.save(producto);
            return "Producto actualizado con exito";
        } else {
            throw new IllegalArgumentException("No existe el producto con el id: " + id);
        }
    }

    public void eliminarProductoPorId(int id) {
        if(productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("No existe el producto con el id: " + id);
        }
    }
}
