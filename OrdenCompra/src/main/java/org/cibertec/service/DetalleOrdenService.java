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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class DetalleOrdenService {
	@Autowired
	private DetalleOrdenRepository detOrdRep;
	
	@Autowired
	private ProductoClient prodCli;
	
	@CircuitBreaker(name = "detalleOrdenService", fallbackMethod = "fallbackGuardar")
    @Retry(name = "detalleOrdenService")
	public DetalleOrden guardar(DetalleOrden det) {
		return detOrdRep.save(det);
	}
	
	@CircuitBreaker(name = "detalleOrdenService", fallbackMethod = "fallbackBuscarPorOrd")
    @Retry(name = "detalleOrdenService")
	public List<DetalleOrden> buscarPorOrd(Orden ord){
		return detOrdRep.findByOrden(ord);
	}
	
	@CircuitBreaker(name = "detalleOrdenService", fallbackMethod = "fallbackBuscarProdPorUsuario")
    @Retry(name = "detalleOrdenService")
	public List<DetalleProducto> buscarProductoPorUsuarioId(Long idUsuario) {
        return detOrdRep.findDetallesProductosByUsuarioId(idUsuario);
    }
	
	
	@CircuitBreaker(name = "detalleOrdenService", fallbackMethod = "fallbackBuscarServPorUsuario")
    @Retry(name = "detalleOrdenService")
	public List<DetalleServicio> buscarServicioPorUsuarioId(Long idUsuario) {
        return detOrdRep.findDetallesServicioByUsuarioId(idUsuario);
    }
	
	
	@CircuitBreaker(name = "detalleOrdenService", fallbackMethod = "fallbackEliminar")
    @Retry(name = "detalleOrdenService")
	public void eliminar(Integer id) {
		detOrdRep.deleteById(id);
	}
	
    @CircuitBreaker(name = "detalleOrdenService", fallbackMethod = "fallbackConsumirStockProductos")
    @Retry(name = "detalleOrdenService")
    public void consumirStockProductos(List<DetalleDto> detalles) {
        for (DetalleDto det : detalles) {
            if (det.getIdProducto() != null) {
                int cantidad = det.getCantidad(); 
                int id = det.getIdProducto().intValue();

                prodCli.actualizarStockProducto(id, cantidad);
            }
        }
    }
    
    
    /* ************************************ */
    /* ************ FallBacks ************* */
    /* ************************************ */

    public DetalleOrden fallbackGuardar(DetalleOrden det, Throwable ex) {
        DetalleOrden detalle =new DetalleOrden();
        detalle.setNombre("Error en guardar DetalleOrden: " + ex.getMessage());
        return detalle;
    }

    public List<DetalleOrden> fallbackBuscarPorOrd(Orden ord, Throwable ex) {
        DetalleOrden detalle = new DetalleOrden();
        detalle.setNombre("Error al buscar detalles por orden: " + ex.getMessage());
        return List.of(detalle);
    }

    public List<DetalleProducto> fallbackBuscarProdPorUsuario(Long idUsuario, Throwable ex) {
        DetalleProducto dp = new DetalleProducto();
        dp.setNombre("Error al obtener productos del usuario: " + ex.getMessage());
        return List.of(dp);
    }

    public List<DetalleServicio> fallbackBuscarServPorUsuario(Long idUsuario, Throwable ex) {
        DetalleServicio ds = new DetalleServicio();
        ds.setNombre("Error al obtener servicios del usuario: " + ex.getMessage());
        return List.of(ds);
    }

    public void fallbackEliminar(Integer id, Throwable ex) {
        System.err.println("Error al eliminar DetalleOrden: " + ex.getMessage());
    }

    public void fallbackConsumirStockProductos(List<DetalleDto> detalles, Throwable ex) {
        System.err.println("Error al consumir stock de productos: " + ex.getMessage());
    }
}
