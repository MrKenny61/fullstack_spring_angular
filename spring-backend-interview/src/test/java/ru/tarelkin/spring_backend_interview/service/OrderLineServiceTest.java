package ru.tarelkin.spring_backend_interview.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.tarelkin.spring_backend_interview.dao.OrderLineDao;
import ru.tarelkin.spring_backend_interview.model.Goods;
import ru.tarelkin.spring_backend_interview.model.GoodsInOrder;
import ru.tarelkin.spring_backend_interview.model.Order;
import ru.tarelkin.spring_backend_interview.model.OrderLine;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderLineServiceTest {
    @InjectMocks
    OrderLineServiceImpl orderLineService;

    @Mock
    OrderLineDao orderLineDao;

    @Test
    public void getGoodsListByOrderIdTest() {
        List<GoodsInOrder> list = Arrays.asList(new GoodsInOrder(1, "goods_1", 2, 3),
                new GoodsInOrder(2, "goods_2", 2, 3),
                new GoodsInOrder(3, "goods_3", 2, 3));

        when(orderLineDao.getGoodsInOrderByOrderId(1)).thenReturn(list);

        List<GoodsInOrder> goodsInOrder = orderLineService.getGoodsListByOrderId(1);

        Assertions.assertThat(goodsInOrder.size()).isEqualTo(3);
        verify(orderLineDao, times(1)).getGoodsInOrderByOrderId(1);
    }

    @Test
    public void findOrderLineByOrderAndGoodsTest() {
        Order order = new Order();
        Goods goods = new Goods();
        OrderLine orderLine = new OrderLine();
        orderLine.setId(1);
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(2);


        when(orderLineDao.findOrderLineByOrderAndGoods(order, goods)).thenReturn(java.util.Optional.of(orderLine));

        OrderLine orderLine1 = orderLineService.findOrderLineByOrderAndGoods(order, goods);

        System.out.println(orderLine1);

        verify(orderLineDao, times(1)).findOrderLineByOrderAndGoods(order, goods);
    }

    @Test
    public void saveOrderLineTest() {
        OrderLine orderLine = new OrderLine();

        orderLineService.save(orderLine);

        verify(orderLineDao, times(1)).save(orderLine);
    }

    @Test
    public void deleteOrderLineTest() {
        OrderLine orderLine = new OrderLine();
        orderLineService.delete(orderLine);

        verify(orderLineDao, times(1)).delete(orderLine);
    }
}
