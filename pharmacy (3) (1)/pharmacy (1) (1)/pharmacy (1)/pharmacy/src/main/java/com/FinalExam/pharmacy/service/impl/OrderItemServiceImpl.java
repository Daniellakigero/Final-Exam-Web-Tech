package com.FinalExam.pharmacy.service.impl;

import com.FinalExam.pharmacy.model.OrderItem;
import com.FinalExam.pharmacy.repo.OrderItemRepository;
import com.FinalExam.pharmacy.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    @Override
    public OrderItem getOrderItem(Long orderItemId) {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);
        return orderItemOptional.orElse(null);
    }
    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        orderItem.setId(id);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getOrdersItemsByOrder(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}