package com.FinalExam.pharmacy.controller.customer;

import com.FinalExam.pharmacy.model.Order;
import com.FinalExam.pharmacy.model.OrderItem;
import com.FinalExam.pharmacy.model.Product;
import com.FinalExam.pharmacy.service.OrderItemService;
import com.FinalExam.pharmacy.service.OrderService;
import com.FinalExam.pharmacy.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/order-items")
@CrossOrigin(origins = "*")
public class OrderItemController {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderItemService.getOrdersItemsByOrder(orderId);
        return ResponseEntity.ok(orderItems);
    }

    @PostMapping("/add")
    public ResponseEntity<OrderItem> addOrderItem(@RequestBody OrderItem orderItem) {

        Long productId = orderItem.getProduct().getId();
        // Check if the product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            logger.error("Product with ID {} not found", productId);
            return ResponseEntity.notFound().build();
        }
        Long orderId = orderItem.getOrder().getId();
        // Check if the order exists
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            logger.error("Order with ID {} not found", orderId);
            return ResponseEntity.notFound().build();
        }
        // Set the price of the order item based on the product price
        orderItem.setPrice(product.getPrice());

        OrderItem addedOrderItem = orderItemService.createOrderItem(orderItem);

        if (addedOrderItem != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedOrderItem);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem updatedOrderItem) {
        logger.info("Updating order item with ID: {}", id);

        OrderItem existingOrderItem = orderItemService.getOrderItem(id);

        if (existingOrderItem != null) {
            // Update order item details here

            OrderItem savedOrderItem = orderItemService.updateOrderItem(id, updatedOrderItem);

            if (savedOrderItem != null) {
                logger.info("Order item updated successfully with ID: {}", id);
                return ResponseEntity.ok(savedOrderItem);
            } else {
                logger.error("Failed to update order item with ID: {}", id);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            logger.warn("Order item with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        logger.info("Deleting order item with ID: {}", id);

        OrderItem orderItem = orderItemService.getOrderItem(id);

        if (orderItem != null) {
            orderItemService.deleteOrderItem(id);
            logger.info("Order item deleted successfully with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Order item with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
