package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Orden;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class OrdenService {
	@Autowired
	private OrdenRepository ordRep;
	
	@CircuitBreaker(name = "ordenService", fallbackMethod = "fallbackGuardar")
	@Retry(name="ordenService")
	public Orden guardar(Orden orden) {
        return ordRep.save(orden);
    }

    @CircuitBreaker(name = "ordenService", fallbackMethod = "fallbackListaOrden")
    @Retry(name="ordenService")
    public List<Orden> listaOrden() {
        return ordRep.findAll();
    }

    @CircuitBreaker(name = "ordenService", fallbackMethod = "fallbackBuscarPorUsuario")
    @Retry(name="ordenService")
    public List<Orden> buscarPorUsuario(Usuario usuario) {
        return ordRep.findByUsuario(usuario);
    }

    @CircuitBreaker(name = "ordenService", fallbackMethod = "fallbackBuscarPorId")
    @Retry(name="ordenService")
    public Optional<Orden> buscarPorId(int id) {
        return ordRep.findById(id);
    }

    @CircuitBreaker(name = "ordenService", fallbackMethod = "fallbackEliminar")
    @Retry(name="ordenService")
    public void eliminar(Integer id) {
        ordRep.deleteById(id);
    }
	
	/*/*****************************/
	/********** FallBacks **********/ 
	/*/*****************************/
	
    public Orden fallbackGuardar(Orden orden, Throwable ex) {
        System.err.println("Fallback en guardar(): " + ex.getMessage());
        return new Orden();
    }

    public List<Orden> fallbackListaOrden(Throwable ex) {
        System.err.println("Fallback en listaOrden(): " + ex.getMessage());
        return List.of();
    }

    public List<Orden> fallbackBuscarPorUsuario(Usuario usuario, Throwable ex) {
        System.err.println("Fallback en buscarPorUsuario(usuarioId=" + usuario.getIdUsuario() + "): " + ex.getMessage());
        return List.of();
    }

    public Optional<Orden> fallbackBuscarPorId(int id, Throwable ex) {
        System.err.println("Fallback en buscarPorId(id=" + id + "): " + ex.getMessage());
        return Optional.empty();
    }

    public void fallbackEliminar(Integer id, Throwable ex) {
        System.err.println("Fallback en eliminar(id=" + id + "): " + ex.getMessage());
    }
}
