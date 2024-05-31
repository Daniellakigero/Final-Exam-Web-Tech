package com.FinalExam.pharmacy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[^0-9]*$", message = "Name must not contain numbers")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 5, max = 200, message = "Description must be between 5 and 200 characters")
    @Pattern(regexp = "^[^0-9]*$", message = "Description must not contain numbers")
    private String description;

    @ManyToOne
    private Users user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}











// package  com.FinalExam.pharmacy.model;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import jakarta.persistence.*;
// import lombok.Data;
// import java.util.List;

// @Entity
// @Table(name = "categories")
// @Data
// public class Category {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String name;
//     private String description;

//     @ManyToOne
//     private Users user;

//     @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//     @JsonIgnore
//     private List<Product> products;
// }