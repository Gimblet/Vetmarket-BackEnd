package com.cibertec.email.service;

import com.cibertec.email.client.IUsuarioClient;
import com.cibertec.email.dto.ProductoRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cibertec.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
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
            Usuario usuario = usuarioClient.getById(producto.getIdUsuario());

            System.out.println(">>>>>>>>>>> Nuevo Mensaje >>>>>>>>>>");
            System.out.println("Asunto: Nuevo producto de " + usuario.getUsername());
            System.out.println("Destinatario: " + usuario.getCorreo());
            System.out.println("Cuerpo:");
            System.out.println(">> Hola, " + usuario.getUsername() + " acaba de agregar un nuevo producto a su tienda virtual!");
            System.out.println(">> Producto: " + producto.getNombre());
            System.out.println(">> Descripcion: " + producto.getDescripcion());
            System.out.println(">> Precio: " + producto.getPrecio());
            System.out.println(">> Stock: " + producto.getStock());
            System.out.println("\nEnviando correo Electronico...\n");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
