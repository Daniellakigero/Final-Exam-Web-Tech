package com.FinalExam.pharmacy.repo;

import  com.FinalExam.pharmacy.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // You can add custom query methods here if needed
    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(String status);
}
