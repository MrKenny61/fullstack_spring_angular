package ru.tarelkin.spring_backend_interview.service;

import ru.tarelkin.spring_backend_interview.model.Order;
import ru.tarelkin.spring_backend_interview.model.OrderInfo;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Integer id);
    OrderInfo getOrderById(Integer id);
    Order save(Order order);
    void deleteById(Integer id);
}
