package org.cibertec.service;

import java.util.List;

import org.cibertec.entity.DetalleServicio;
import org.cibertec.entity.Orden;
import org.cibertec.repository.DetalleServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleServicioService {
	@Autowired
	private DetalleServicioRepository detSerRep;
	
	public DetalleServicio guardar(DetalleServicio detSer) {
		return detSerRep.save(detSer);
	}
	
	public List<DetalleServicio> buscarPorOrd(Orden ord){
		return detSerRep.findByOrden(ord);
	}
	
	public List<DetalleServicio> buscarPorUsuarioId(Long idUsuario) {
        return detSerRep.findDetallesByUsuarioId(idUsuario);
    }
}
