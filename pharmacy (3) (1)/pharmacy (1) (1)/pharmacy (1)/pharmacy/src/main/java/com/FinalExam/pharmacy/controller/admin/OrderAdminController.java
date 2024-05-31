package com.FinalExam.pharmacy.controller.admin;

import com.FinalExam.pharmacy.model.Order;
import com.FinalExam.pharmacy.model.UserRole;
import com.FinalExam.pharmacy.model.Users;
import com.FinalExam.pharmacy.service.OrderService;
import com.FinalExam.pharmacy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import  java.util.List;
@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin(origins = "*")
public class OrderAdminController {
    private static final Logger logger = LoggerFactory.getLogger(OrderAdminController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        logger.info("Updating order with ID: {}", id);

        Order existingOrder = orderService.getOrder(id);

        if (existingOrder != null) {
            // Update order details here

            existingOrder.setStatus(updatedOrder.getStatus());
            Order savedOrder = orderService.updateOrder(id, existingOrder);
            if (savedOrder != null) {
                logger.info("Order updated successfully with ID: {}", id);

                return ResponseEntity.ok(savedOrder);
            } else {
                logger.error("Failed to update order with ID: {}", id);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            logger.warn("Order with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {

        logger.info("Deleting order with ID: {}", id);

        Order order = orderService.getOrder(id);

        if (order != null) {

            Long userId = order.getUser().getId();

            logger.info("Customer ID in session: {}", userId);

            if (userId == null) {
                // Handle the case where the admin ID is not found in the session
                logger.error("Admin ID not found in session");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Users customer = userService.getUserById(userId);

            if (customer != null && customer.getRole() == UserRole.CUSTOMER) {

                orderService.deleteOrder(id);
                logger.info("order deleted successfully with ID: {}", id);
                return ResponseEntity.noContent().build();

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            logger.warn("Order with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }
}
