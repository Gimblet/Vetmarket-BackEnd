package org.cibertec.service;

import java.util.Date;
import java.util.List;

import org.cibertec.client.CarritoClient;
import org.cibertec.client.ProductoClient;
import org.cibertec.client.ServicioCitaClient;
import org.cibertec.dto.DetalleDto;
import org.cibertec.entity.DetalleOrden;
import org.cibertec.entity.DetalleProducto;
import org.cibertec.entity.DetalleServicio;
import org.cibertec.entity.Orden;
import org.cibertec.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenService {
	@Autowired
	private DetalleOrdenRepository detOrdRep;
	
	@Autowired
	private ProductoClient prodCli;
	
	@Autowired
	private CarritoClient cCli;
	
	@Autowired
	private ServicioCitaClient serCitCli;

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

    public void consumirStockProductos(Integer id, int cantidad) {
    	prodCli.restarStockProductoPorID(id, cantidad);
    }

    public List<DetalleDto> carritoPorUsuario(String token,Long idUsuario) {
        ResponseEntity<List<DetalleDto>> response = cCli.obtenerCarrito(token,idUsuario);
        List<DetalleDto> detalles = response.getBody();
        
        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("El carrito está vacío o no se pudo obtener.");
        }
        return detalles;
    }

    public void eliminarProductoCarrito(String token, Long idUsuario, Integer idProducto) {
    	cCli.eliminarProducto(token, idUsuario, idProducto);
    }

    public DetalleDto citaPorUsuario(String token, Long idUsuario, Integer idServicio, Long idMascota, Date fechaCita) {
        ResponseEntity<DetalleDto> response = serCitCli.nuevaCita(token, idUsuario, idServicio, idMascota, fechaCita);
        DetalleDto detalle = response.getBody();
     
        if (detalle == null) {
            throw new RuntimeException("No se pudo obtener la cita del usuario.");
        }
        return detalle;
    }

}
