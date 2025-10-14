package org.cibertec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.cibertec.dto.DetalleVentaDTO;
import org.cibertec.entity.DetalleOrden;
import org.cibertec.entity.DetalleProducto;
import org.cibertec.entity.DetalleServicio;
import org.cibertec.entity.Orden;
import org.cibertec.entity.Usuario;
import org.cibertec.service.DetalleOrdenService;
import org.cibertec.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
		
		return ResponseEntity.ok(detalle);
	}
	
	//Detalle de Ventas para veterinarios
	@GetMapping("/listaVentas")
	public ResponseEntity<List<DetalleVentaDTO>> ListarVentasVeterinarios(@RequestParam("usuarioId") Long idUsuario){
		//si el gateway garantiza el envio se elimina la validacion if
        if (idUsuario == null) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Acceso denegado: El ID de Cliente no fue proporcionado en el encabezado.");
        }
        
        List<DetalleProducto> detalleProd = detOrdSer.buscarProductoPorUsuarioId(idUsuario);
        List<DetalleServicio> detalleServ = detOrdSer.buscarServicioPorUsuarioId(idUsuario);
        
        
        List<DetalleVentaDTO> detallesCombinados= new ArrayList<>();
        
        for(DetalleOrden detOrd:detalleProd) {
        	detallesCombinados.add(mapADto(detOrd));
        }
        for(DetalleOrden detOrd:detalleServ) {
        	detallesCombinados.add(mapADto(detOrd));
        }
        
        return ResponseEntity.ok(detallesCombinados);
        
	}
	
	
	
	
	
	private DetalleVentaDTO mapADto(DetalleOrden detOrd) {
	    DetalleVentaDTO dto = new DetalleVentaDTO();
	    dto.setDetalleId(detOrd.getIdDetalle());
	    dto.setOrdenId(detOrd.getOrden().getNumeroOrden());
	    dto.setNombreItem(detOrd.getNombre());
	    dto.setPrecio(detOrd.getPrecio());
	    dto.setTotal(detOrd.getTotal());
	    
	    if(detOrd instanceof DetalleProducto) {
	    	DetalleProducto detProd= (DetalleProducto) detOrd;
	    	dto.setTipoItem("Producto");
	    	dto.setCantidad(detProd.getCantidad());
	    	dto.setFecha(null);
	    } 
	    if (detOrd instanceof DetalleServicio) {
	        DetalleServicio detServ = (DetalleServicio) detOrd;
	        dto.setTipoItem("Servicio");
	        dto.setCantidad(1);
	        dto.setFecha(detServ.getFechaCita());
	    }
	    return dto;
	}
}
