package org.cibertec.service;

import java.util.List;

import org.cibertec.entity.DetalleOrdenCompra;
import org.cibertec.entity.Orden;
import org.cibertec.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenService {
	@Autowired
	private DetalleOrdenRepository detOrdRep;
	
	public DetalleOrdenCompra guardar(DetalleOrdenCompra det) {
		return detOrdRep.save(det);
	}
	
	public List<DetalleOrdenCompra> buscarPorOrd(Orden ord){
		return detOrdRep.findByOrden(ord);
	}
	
	public List<DetalleOrdenCompra> buscarPorUsuarioId(Long idUsuario) {
        return detOrdRep.findDetallesByUsuarioId(idUsuario);
    }
}
