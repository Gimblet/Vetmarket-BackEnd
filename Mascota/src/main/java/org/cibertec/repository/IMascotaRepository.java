package org.cibertec.repository;

import org.cibertec.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByUsuarioIdUsuario(Long idUsuario);
}
