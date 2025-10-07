package org.cibertec.repository;

import java.util.List;

import org.cibertec.entity.DetalleOrdenCompra;
import org.cibertec.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrdenCompra, Integer>{
	List<DetalleOrdenCompra> findByOrden(Orden orden);

	@Query("SELECT d FROM DetalleOrdenCompra d WHERE d.producto.usuario.idUsuario = :idUsuario")
    List<DetalleOrdenCompra> findDetallesByUsuarioId(@Param("idUsuario") Long idUsuario);
	
}
