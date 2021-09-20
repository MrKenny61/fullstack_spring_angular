package ru.tarelkin.spring_backend_interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tarelkin.spring_backend_interview.dao.OrderLineDao;
import ru.tarelkin.spring_backend_interview.model.Goods;
import ru.tarelkin.spring_backend_interview.model.GoodsInOrder;
import ru.tarelkin.spring_backend_interview.model.Order;
import ru.tarelkin.spring_backend_interview.model.OrderLine;

import java.util.List;

@Service
public class OrderLineServiceImpl implements OrderLineService{
    private OrderLineDao orderLineDao;

    @Autowired
    public OrderLineServiceImpl(OrderLineDao orderLineDao) {
        this.orderLineDao = orderLineDao;
    }

    @Override
    public List<GoodsInOrder> getGoodsListByOrderId(Integer id) {
        return orderLineDao.getGoodsInOrderByOrderId(id);
    }

    @Override
    public OrderLine findOrderLineByOrderAndGoods(Order order, Goods goods) {
        return orderLineDao.findOrderLineByOrderAndGoods(order, goods).orElseThrow();
    }

    @Override
    public OrderLine save(OrderLine orderLine) {
        return orderLineDao.save(orderLine);
    }

    @Override
    public void delete(OrderLine orderLine) {
        orderLineDao.delete(orderLine);
    }
}
