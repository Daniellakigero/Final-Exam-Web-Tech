package com.FinalExam.pharmacy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price should be a positive value")
    @Max(value = 9999, message = "Price should not exceed 9999.99")
    private double price;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity should be a positive number")
    @Max(value = 10000, message = "Quantity should not exceed 10000")
    private int quantity;

    @ManyToOne
    @NotNull(message = "Category is required")
    private Category category;

    @Lob
    private byte[] image;
}


















// package com.FinalExam.pharmacy.model;

// import jakarta.persistence.*;
// import lombok.Data;
// @Entity
// @Table(name = "products")
// @Data
// public class Product {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String name;

//     private String description;

//     private double price;

//     private int quantity;

//     @ManyToOne
//     private Category category;

//     @Lob
//     private byte[] image;
// }