package org.cibertec.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cibertec.dto.ProductoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaPublisherService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic.name}")
    private String topicName;

    public void send(ProductoRequestDTO producto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String msg = mapper.writeValueAsString(producto);

            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, msg);
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    System.out.println("Error al enviar el mensaje: " + ex.getMessage());
                } else {
                    System.out.println("Mensaje enviado con exito: " + msg +
                            " en el tema: " + topicName + " Offset: " +
                            result.getRecordMetadata().offset());
                }
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
