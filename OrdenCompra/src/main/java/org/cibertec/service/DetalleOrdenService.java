package org.cibertec.service;

import java.util.List;

import org.cibertec.client.ProductoClient;
import org.cibertec.dto.DetalleDto;
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
	
	@Autowired
	private ProductoClient prodCli;
	
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
	
	public void eliminar(Integer id) {
		detOrdRep.deleteById(id);
	}
	
    public void consumirStockProductos(List<DetalleDto> detalles) {
        for (DetalleDto det : detalles) {
            if (det.getIdProducto() != null) {
                int cantidad = det.getCantidad(); 
                int id = det.getIdProducto().intValue();

                prodCli.actualizarStockProducto(id, cantidad);
            }
        }
    }
}
