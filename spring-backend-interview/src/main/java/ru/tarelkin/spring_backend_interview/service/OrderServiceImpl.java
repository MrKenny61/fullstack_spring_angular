package ru.tarelkin.spring_backend_interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tarelkin.spring_backend_interview.dao.OrderDao;
import ru.tarelkin.spring_backend_interview.dao.OrderLineDao;
import ru.tarelkin.spring_backend_interview.model.GoodsList;
import ru.tarelkin.spring_backend_interview.model.Order;
import ru.tarelkin.spring_backend_interview.model.OrderInfo;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderDao orderDao;
    private OrderLineDao orderLineDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderLineDao orderLineDao) {
        this.orderDao = orderDao;
        this.orderLineDao = orderLineDao;
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(Integer id) {
        return orderDao.findById(id).orElseThrow();
    }

    @Override
    public OrderInfo getOrderById(Integer id) {
        Order clientInfo = orderDao.findById(id).orElseThrow();

        List<GoodsList> goodsInList = orderLineDao.getGoodsListByOrderId(id);

        OrderInfo orderinfo = new OrderInfo();
        orderinfo.setClientInfo(clientInfo);
        orderinfo.setGoodsInList(goodsInList);

        return orderinfo;
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
