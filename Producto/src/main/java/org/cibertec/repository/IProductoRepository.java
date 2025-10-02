package org.cibertec.repository;

import org.cibertec.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProductoRepository extends JpaRepository<Producto, Integer>{
    Optional<List<Producto>> findAllByNombreIgnoreCaseContaining(String nombre);

}
