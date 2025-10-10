package org.cibertec.repository;

import org.cibertec.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMascotaRepository extends JpaRepository<Mascota, Long> {
}
