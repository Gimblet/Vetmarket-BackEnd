package org.cibertec.repository;

import java.util.List;

import org.cibertec.entity.Orden;
import org.cibertec.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer>{
	List<Orden> findByUsuario(Usuario usuario);
}
