package com.portfolio.inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class KafkaConsumer {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "topic_compras", groupId = "ecommerce-group")
    public void consumeOrderEvent(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long orderId = jsonNode.get("id").asLong();
            Long productId = jsonNode.get("productId").asLong();
            int quantity = jsonNode.get("quantity").asInt();

            Optional<Product> productOpt = productRepository.findById(productId);
            
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                if (product.getStock() >= quantity) {
                    product.setStock(product.getStock() - quantity);
                    productRepository.save(product);
                    System.out.println("✅ [INVENTARIO] Stock descontado. Aprobando orden " + orderId);
                    kafkaTemplate.send("topic_ordenes_respuestas", "{\"orderId\":" + orderId + ", \"status\":\"APPROVED\"}");
                } else {
                    System.out.println("❌ [INVENTARIO] Sin stock. Rechazando orden " + orderId);
                    kafkaTemplate.send("topic_ordenes_respuestas", "{\"orderId\":" + orderId + ", \"status\":\"REJECTED\"}");
                }
            } else {
                System.out.println("❌ [INVENTARIO] Producto no existe. Rechazando orden " + orderId);
                kafkaTemplate.send("topic_ordenes_respuestas", "{\"orderId\":" + orderId + ", \"status\":\"REJECTED\"}");
            }

        } catch (Exception e) {
            System.out.println("❌ [KAFKA] Error procesando el mensaje: " + e.getMessage());
        }
    }
}
