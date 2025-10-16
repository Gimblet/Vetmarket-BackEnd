package org.cibertec.repository;

import java.util.Date;

import org.cibertec.entity.DetalleServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioCitaRepository extends JpaRepository<DetalleServicio, Integer>{
	@Query("SELECT COUNT(d) FROM DetalleServicio d WHERE d.servicio.id = :idServicio AND DATE(d.fechaCita) = DATE(:fechaCita)")
	public Integer contarCitaPorServicioYFecha(Integer idServicio,Date fechaCita);
}
