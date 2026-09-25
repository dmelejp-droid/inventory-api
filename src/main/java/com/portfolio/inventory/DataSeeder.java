package com.portfolio.inventory;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. Validar si hay menos de 50 productos para rellenar la base de datos
        if (productRepository.count() < 50) {
            
            Faker faker = new Faker();
            
            // 2. Crear 50 productos ficticios
            for (int i = 0; i < 50; i++) {
                Product product = new Product();
                
                // Usamos faker para inventar nombres y datos
                product.setName(faker.commerce().productName());
                product.setPrice(Double.valueOf(faker.commerce().price().replace(",", ".")));
                product.setStock(faker.number().numberBetween(10, 100));
                
                // 3. Guardar en la base de datos
                productRepository.save(product);
            }
            
            System.out.println("✅ 50 productos ficticios creados con Faker!");
        }
    }
}