package  com.FinalExam.pharmacy.service;

import  com.FinalExam.pharmacy.model.OrderItem;
import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);
    OrderItem getOrderItem(Long orderItemId);
    void deleteOrderItem(Long orderItemId);
    OrderItem updateOrderItem(Long id, OrderItem orderItem);
    List<OrderItem> getOrdersItemsByOrder(Long orderId);
}