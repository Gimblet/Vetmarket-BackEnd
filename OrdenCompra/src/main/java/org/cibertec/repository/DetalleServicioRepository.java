package org.cibertec.repository;

import java.util.List;

import org.cibertec.entity.DetalleServicio;
import org.cibertec.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleServicioRepository extends JpaRepository<DetalleServicio, Integer>{
	List<DetalleServicio> findByOrden(Orden orden);

	@Query("SELECT d FROM DetalleServicio d WHERE d.servicio.usuario.idUsuario = :idUsuario")
    List<DetalleServicio> findDetallesByUsuarioId(@Param("idUsuario") Long idUsuario);
	
}
