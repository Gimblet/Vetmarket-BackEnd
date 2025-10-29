package com.cibertec.email.service;

import com.cibertec.email.client.IUsuarioClient;
import com.cibertec.email.dto.ProductoRequestDTO;
import com.cibertec.email.dto.ServicioRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.cibertec.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailKafkaSuscriberService {
    @Autowired
    IUsuarioClient usuarioClient;

    @KafkaListener(topics = "${app.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ProductoRequestDTO producto = mapper.readValue(msg, ProductoRequestDTO.class);
            ResponseEntity<List<Usuario>> response = usuarioClient.buscarUsuariosPorRol(3L);
            List<Usuario> usuarios = response.getBody();

            System.out.println(">>>>>>>>>>> Nuevo Mensaje >>>>>>>>>>");
            if (usuarios != null && !usuarios.isEmpty()) {
                for (Usuario usuario : usuarios) {
                    System.out.println("------------------------------------");
                    System.out.println("Asunto: Nuevo producto disponible");
                    System.out.println("Destinatario: " + usuario.getCorreo());
                    System.out.println("Cuerpo:");
                    System.out.println(">> Hola, " + usuario.getNombre() + " "+usuario.getApellido()+ "!");
                    System.out.println(">> Se ha agregado un nuevo producto a la tienda virtual.");
                    System.out.println(">> Producto: " + producto.getNombre());
                    System.out.println(">> Descripción: " + producto.getDescripcion());
                    System.out.println(">> Precio: S/ " + producto.getPrecio());
                    System.out.println(">> Solo hay " + producto.getStock()+" productos disponibles");
                    System.out.println(">> ¡No te lo pierdas!\n");
                }
            }
            System.out.println("\nEnviando correo Electronico...\n");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    // Servicio

    @KafkaListener(topics = "${app.servicio.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void Escuchar(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {

        	if (message.startsWith("\"") && message.endsWith("\"")) {
                message = message.substring(1, message.length() - 1)
                                 .replace("\\\"", "\"");
            }
        	
            ServicioRequestDTO servicio = mapper.readValue(message, ServicioRequestDTO.class);
            // Buscar usuario Clientes
            ResponseEntity<List<Usuario>> response = usuarioClient.buscarUsuariosPorRol(3L);
            List<Usuario> usuarios = response.getBody();

            System.out.println(">>>>>>>>>>> Nuevo Mensaje >>>>>>>>>>");
            if (usuarios != null || !usuarios.isEmpty()) {
            	for (Usuario usuario : usuarios) {
                    System.out.println("------------------------------------");
                    System.out.println("Asunto: Nuevo servicio disponible");
                    System.out.println("Destinatario: " + usuario.getCorreo());
                    System.out.println("Cuerpo:");
                    System.out.println(">> Hola, " + usuario.getNombre() + " " + usuario.getApellido() + "!");
                    System.out.println(">> Se ha agregado un nuevo servicio al catálogo.");
                    System.out.println(">> Servicio: " + servicio.getNombre());
                    System.out.println(">> Descripción: " + servicio.getDescripcion());
                    System.out.println(">> Precio: S/ " + servicio.getPrecio());
                    System.out.println(">> ¡Aprovecha esta nueva opción para tu mascota!\n");
                }
            }
            System.out.println("\nEnviando correo Electronico...\n");

        } catch (Exception e) { // Mejor que JsonProcessingException solo
            System.err.println("Error procesando mensaje de Kafka: " + message);
            e.printStackTrace(); // O loguea adecuadamente
        }
    }
}
