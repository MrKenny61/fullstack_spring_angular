package ru.tarelkin.spring_backend_interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tarelkin.spring_backend_interview.dao.OrderDao;
import ru.tarelkin.spring_backend_interview.exception.NotFoundException;
import ru.tarelkin.spring_backend_interview.model.Order;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(Integer id) {
        return orderDao.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Order save(Order order) {
        return orderDao.save(order);
    }

    @Override
    public void deleteById(Integer id) {
        orderDao.deleteById(id);
    }
}
