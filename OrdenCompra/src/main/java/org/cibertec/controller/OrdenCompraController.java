package org.cibertec.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.cibertec.dto.DetalleVentaDTO;
import org.cibertec.entity.DetalleOrdenCompra;
import org.cibertec.entity.DetalleServicio;
import org.cibertec.entity.Orden;
import org.cibertec.entity.Usuario;
import org.cibertec.service.DetalleOrdenService;
import org.cibertec.service.DetalleServicioService;
import org.cibertec.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/veterinaria")
public class OrdenCompraController {
	@Autowired
	private OrdenService oSer;
	
	@Autowired
	private DetalleOrdenService detOrdSer;
	
	@Autowired
	private DetalleServicioService detServSer;
	
	//todas las ordenes para el administrador
	@GetMapping("/ordenes")
	public ResponseEntity<List<Orden>> listarOrdenes(){
		List<Orden> ordenes =oSer.listaOrden();
		return ResponseEntity.ok(ordenes);
	}
	
	//Listar las ordenes de cada cliente
	@GetMapping("/ordenesClientes")
	public ResponseEntity<List<Orden>> ListarOrdenPorUsuario(@RequestParam("usuarioId") Long idUsuario){
		//si el gateway garantiza el envio se elimina la validacion if
        if (idUsuario == null) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Acceso denegado: El ID de Cliente no fue proporcionado en el encabezado.");
        }
        
        Usuario usuario=new Usuario();
        usuario.setIdUsuario(idUsuario);
        List<Orden> ordenes = oSer.buscarPorUsuario(usuario);
		return ResponseEntity.ok(ordenes);
	}
	
	//Detalle de Ordenes
	@GetMapping("/detalleOrden/{idOrden}")
	public ResponseEntity<?> listarDetalleOrden(@PathVariable int idOrden){
		Optional<Orden> ordenOpt = oSer.buscarPorId(idOrden);
		
		if (!ordenOpt.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Orden con ID " + idOrden + " no encontrada");
	    }
		Orden orden = ordenOpt.get();
		
	    List<?> detalle = detOrdSer.buscarPorOrd(orden);
	    
	    if (detalle.isEmpty()) {
	        detalle = detServSer.buscarPorOrd(orden);
	    }
		
		return ResponseEntity.ok(detalle);
	}
	
	//Detalle de Ventas para veterinarios
	@GetMapping("/listaVentas")
	public ResponseEntity<List<DetalleVentaDTO>> ListarVentasVeterinarios(@RequestParam("usuarioId") Long idUsuario){
		//si el gateway garantiza el envio se elimina la validacion if
        if (idUsuario == null) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Acceso denegado: El ID de Cliente no fue proporcionado en el encabezado.");
        }
        
        List<DetalleOrdenCompra> detalleProd = detOrdSer.buscarPorUsuarioId(idUsuario);
        List<DetalleServicio> detalleServ = detServSer.buscarPorUsuarioId(idUsuario);
        
        List<DetalleVentaDTO> detallesCombinados= new ArrayList<>();
        
        for(DetalleOrdenCompra detProd:detalleProd) {
        	detallesCombinados.add(mapADto(detProd));
        }
        for(DetalleServicio detServ:detalleServ) {
        	detallesCombinados.add(mapADto(detServ));
        }
        
        
        return ResponseEntity.ok(detallesCombinados);
        
	}
	
	private DetalleVentaDTO mapADto(DetalleOrdenCompra detProd) {
	    DetalleVentaDTO dto = new DetalleVentaDTO();
	    dto.setDetalleId(detProd.getIdDetalleOrden());
	    dto.setOrdenId(detProd.getOrden().getNumeroOrden()); 
	    dto.setTipoItem("PRODUCTO");
	    dto.setNombreItem(detProd.getNombreProducto());
	    dto.setPrecio(detProd.getPrecio());
	    dto.setCantidad(detProd.getCantidad());
	    dto.setTotal(detProd.getTotal());
	    dto.setFecha(detProd.getOrden().getFecha());
	    return dto;
	}
	
	private DetalleVentaDTO mapADto(DetalleServicio detServ) {
	    DetalleVentaDTO dto = new DetalleVentaDTO();
	    dto.setDetalleId(detServ.getIdDetalleServicio());
	    dto.setOrdenId(detServ.getOrden().getNumeroOrden());
	    dto.setTipoItem("SERVICIO");
	    dto.setNombreItem(detServ.getNombreServicio());
	    dto.setPrecio(detServ.getPrecio());
	    dto.setCantidad(1);
	    dto.setTotal(detServ.getPrecio());
	    dto.setFecha(detServ.getFechaCita());
	    return dto;
	}
}
