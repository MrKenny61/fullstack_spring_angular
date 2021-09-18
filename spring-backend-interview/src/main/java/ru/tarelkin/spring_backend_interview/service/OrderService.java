package ru.tarelkin.spring_backend_interview.service;

import ru.tarelkin.spring_backend_interview.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();
    Order findById(Integer id);
    Order save(Order order);
    void deleteById(Integer id);
}
