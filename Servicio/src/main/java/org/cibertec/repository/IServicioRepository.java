package org.cibertec.repository;

import org.cibertec.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioRepository extends JpaRepository<Servicio, Integer> {
}
