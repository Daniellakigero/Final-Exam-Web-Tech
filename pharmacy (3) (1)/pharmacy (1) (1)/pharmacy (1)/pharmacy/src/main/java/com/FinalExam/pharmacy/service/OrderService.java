package  com.FinalExam.pharmacy.service;
import  com.FinalExam.pharmacy.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
    Order getOrder(Long orderId);
    List<Order> getOrders();
    List<Order> getOrdersByStatus(String status);
    List<Order> getOrdersByUser(Long userId);
}
