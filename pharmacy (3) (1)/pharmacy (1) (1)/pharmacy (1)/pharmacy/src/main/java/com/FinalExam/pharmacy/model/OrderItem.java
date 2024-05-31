// package com.FinalExam.pharmacy.model;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.*;
// import lombok.Data;

// @Entity
// @Table(name = "order_items", uniqueConstraints = {
//     @UniqueConstraint(columnNames = {"order_id", "product_id"})
// })
// @Data
// public class OrderItem {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne
//     @NotNull(message = "Order cannot be null")
//     @JoinColumn(name = "order_id")
//     private Order order;

//     @ManyToOne
//     @NotNull(message = "Product cannot be null")
//     @JoinColumn(name = "product_id")
//     private Product product;

//     @Min(value = 1, message = "Quantity must be at least 1")
//     @Positive(message = "Quantity must be a positive number")
//     private int quantity;

//     @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
//     @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
//     private double price;
// }
















package com.FinalExam.pharmacy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Order cannot be null")
    private Order order;

    @ManyToOne
    @NotNull(message = "Product cannot be null")
    private Product product;
    // @Min(value = 1, message = "Quantity must be at least 1")
    // @Positive(message = "Quantity must be a positive number")
    private int quantity;
    // @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    // @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
    private double price;
}