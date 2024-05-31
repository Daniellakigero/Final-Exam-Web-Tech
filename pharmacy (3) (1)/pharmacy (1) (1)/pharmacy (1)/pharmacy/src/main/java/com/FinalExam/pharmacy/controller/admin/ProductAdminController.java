package com.FinalExam.pharmacy.controller.admin;

import com.FinalExam.pharmacy.model.*;
import com.FinalExam.pharmacy.service.ProductService;
import com.FinalExam.pharmacy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "*")
public class ProductAdminController {

    private static final Logger logger = LoggerFactory.getLogger(ProductAdminController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Long adminId = product.getCategory().getUser().getId();

        logger.info("Admin ID in session: {}", adminId);

        if (adminId == null) {
            logger.error("Admin ID not found in session");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Log the retrieved values
        logger.info("Admin ID: {}", adminId);
        logger.info("Product Name: {}", product.getName());
        logger.info("Product Description: {}", product.getDescription());
        logger.info("Product Price: {}", product.getPrice());
        logger.info("Product Quantity: {}", product.getQuantity());
        logger.info("Product Category: {}", product.getCategory());

        Users admin = userService.getUserById(adminId);

        if (admin != null && admin.getRole() == UserRole.ADMIN) {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        logger.info("Updating product with ID: {}", id);

        Long adminId = updatedProduct.getCategory().getUser().getId();
        logger.info("Admin ID in session: {}", adminId);

        if (adminId == null) {
            // Handle the case where the admin ID is not found in the session
            logger.error("Admin ID not found in session");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Product existingProduct = productService.getProductById(id);

        if (existingProduct != null) {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setImage(updatedProduct.getImage());

            Users admin = userService.getUserById(adminId);

            if (admin != null && admin.getRole() == UserRole.ADMIN) {
                Product savedProduct = productService.updateProduct(id, existingProduct);

                if (savedProduct != null) {
                    logger.info("Product updated successfully with ID: {}", id);

                    return ResponseEntity.ok(savedProduct);
                } else {
                    logger.error("Failed to update product with ID: {}", id);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            logger.warn("Product with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        Product product = productService.getProductById(id);

        if (product != null) {
            Long adminId = product.getCategory().getUser().getId();
            logger.info("Admin ID in session: {}", adminId);

            if (adminId == null) {
                // Handle the case where the admin ID is not found in the session
                logger.error("Admin ID not found in session");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Users admin = userService.getUserById(adminId);

            if (admin != null && admin.getRole() == UserRole.ADMIN) {
                productService.deleteProduct(id);
                logger.info("Product deleted successfully with ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            logger.warn("Product with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}