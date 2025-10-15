package org.cibertec.repository;

import java.util.List;

import org.cibertec.entity.DetalleOrden;
import org.cibertec.entity.DetalleProducto;
import org.cibertec.entity.DetalleServicio;
import org.cibertec.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer>{
	List<DetalleOrden> findByOrden(Orden orden);

	@Query("SELECT d FROM DetalleProducto d WHERE d.producto.usuario.idUsuario = :idUsuario")
    List<DetalleProducto> findDetallesProductosByUsuarioId(@Param("idUsuario") Long idUsuario);
	
	@Query("SELECT d FROM DetalleServicio d WHERE d.servicio.usuario.idUsuario = :idUsuario")
    List<DetalleServicio> findDetallesServicioByUsuarioId(@Param("idUsuario") Long idUsuario);
}
