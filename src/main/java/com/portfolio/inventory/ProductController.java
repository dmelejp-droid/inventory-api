package com.portfolio.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Le pedimos a Spring que nos "inyecte" el repositorio que creamos antes
    @Autowired
    private ProductRepository productRepository;

    // 1. Obtener todos los productos (GET /api/products)
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Obtener un producto por su ID (GET /api/products/1)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get()); // Retorna código HTTP 200 OK con el producto
        } else {
            return ResponseEntity.notFound().build(); // Retorna código HTTP 404 No Encontrado
        }
    }

    // 3. Crear un nuevo producto (POST /api/products)
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 4. Eliminar un producto (DELETE /api/products/1)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}