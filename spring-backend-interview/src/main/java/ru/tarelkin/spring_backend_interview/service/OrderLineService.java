package ru.tarelkin.spring_backend_interview.service;

import ru.tarelkin.spring_backend_interview.model.Goods;
import ru.tarelkin.spring_backend_interview.model.GoodsInOrder;
import ru.tarelkin.spring_backend_interview.model.Order;
import ru.tarelkin.spring_backend_interview.model.OrderLine;

import java.util.List;

public interface OrderLineService {
    List<GoodsInOrder> getGoodsListByOrderId(Integer id);
    OrderLine findOrderLineByOrderAndGoods(Order order, Goods goods);
    OrderLine save(OrderLine orderLine);
    void delete(OrderLine orderLine);
}
