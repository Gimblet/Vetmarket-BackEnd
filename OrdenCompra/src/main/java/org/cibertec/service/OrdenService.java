package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Orden;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenService {
	@Autowired
	private OrdenRepository ordRep;
	
	public Orden guardar(Orden orden) {
		return ordRep.save(orden);
	}
	
	public List<Orden> listaOrden(){
		return ordRep.findAll();
	}
	
	public List<Orden> buscarPorUsuario(Usuario usuario){
		return ordRep.findByUsuario(usuario);
	}
	
	public Optional<Orden> buscarPorId(int id){
		return ordRep.findById(id);
	}
	
	public void eliminar(Integer id) {
		ordRep.deleteById(id);
	}

}
