package com.FinalExam.pharmacy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tracking ID cannot be null")
    private UUID trackingId;

    @ManyToOne
    @NotNull(message = "User cannot be null")
    private Users user;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 100, message = "City must be less than 100 characters")
    private String city;

    @PastOrPresent(message = "Order date cannot be in the future")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private double totalAmount;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "^(PENDING|PROCESSING|SHIPPED|DELIVERED|CANCELLED)$", message = "Status must be one of the following: PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED")
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderItems;

    @NotBlank(message = "Payment method cannot be blank")
    @Pattern(regexp = "^(CREDIT_CARD|PAYPAL|CASH_ON_DELIVERY)$", message = "Payment method must be one of the following: CREDIT_CARD, PAYPAL, CASH_ON_DELIVERY")
    private String paymentMethod;
}


















// package com.FinalExam.pharmacy.model;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import jakarta.persistence.*;
// import java.util.Date;
// import lombok.Data;
// import java.util.List;
// import java.util.UUID;

// @Entity
// @Table(name = "orders")
// @Data
// public class Order {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private UUID trackingId;

//     @ManyToOne
//     private Users user;

//     private String address;

//     private String city;

//     private Date orderDate;

//     private double totalAmount;

//     private String status;

//     @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//     @JsonIgnore
//     private List<OrderItem> orderItems;

//     private String paymentMethod;
// }
