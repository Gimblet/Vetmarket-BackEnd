package org.cibertec.service;

import java.util.List;

import org.cibertec.entity.DetalleOrden;
import org.cibertec.entity.DetalleProducto;
import org.cibertec.entity.DetalleServicio;
import org.cibertec.entity.Orden;
import org.cibertec.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenService {
	@Autowired
	private DetalleOrdenRepository detOrdRep;
	
	public DetalleOrden guardar(DetalleOrden det) {
		return detOrdRep.save(det);
	}
	
	public List<DetalleOrden> buscarPorOrd(Orden ord){
		return detOrdRep.findByOrden(ord);
	}
	
	public List<DetalleProducto> buscarProductoPorUsuarioId(Long idUsuario) {
        return detOrdRep.findDetallesProductosByUsuarioId(idUsuario);
    }
	
	public List<DetalleServicio> buscarServicioPorUsuarioId(Long idUsuario) {
        return detOrdRep.findDetallesServicioByUsuarioId(idUsuario);
    }
}
