package ru.tarelkin.spring_backend_interview.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.tarelkin.spring_backend_interview.dao.OrderDao;
import ru.tarelkin.spring_backend_interview.dao.OrderLineDao;
import ru.tarelkin.spring_backend_interview.model.Goods;
import ru.tarelkin.spring_backend_interview.model.GoodsList;
import ru.tarelkin.spring_backend_interview.model.Order;
import ru.tarelkin.spring_backend_interview.model.OrderInfo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderDao orderDao;
    @Mock
    OrderLineDao orderLineDao;

    @Test
    public void findAllOrdersTest() {
        List<Order> list = Arrays.asList(new Order(1, "client_1", new Date(), "address"),
                new Order(2, "client_2", new Date(), "address"),
                new Order(3, "client_3", new Date(), "address"));

        when(orderDao.findAll()).thenReturn(list);

        List<Order> orderList = orderService.findAll();

        Assertions.assertThat(orderList.size()).isEqualTo(3);
        verify(orderDao, times(1)).findAll();
    }

    @Test
    public void findOrderByIdTest() {
        when(orderDao.findById(1)).thenReturn(java.util.Optional.of(new Order(1, "client_1", new Date(), "address")));

        Order order = orderService.findById(1);

        Assertions.assertThat(order.getClient()).isEqualTo("client_1");
        Assertions.assertThat(order.getAddress()).isEqualTo("address");
        verify(orderDao, times(1)).findById(1);
    }

    @Test
    public void getOrderByIdTest() {
        List<GoodsList> list = Arrays.asList(new GoodsList(1, "goods_1", 2, 3),
                new GoodsList(2, "goods_2", 2, 3),
                new GoodsList(3, "goods_3", 2, 3));

        when(orderLineDao.getGoodsListByOrderId(1)).thenReturn(list);
        when(orderDao.findById(1)).thenReturn(java.util.Optional.of(new Order(1, "client", new Date(), "address")));

        OrderInfo orderInfo = orderService.getOrderById(1);

        verify(orderDao, times(1)).findById(1);
        verify(orderLineDao, times(1)).getGoodsListByOrderId(1);
    }

    @Test
    public void saveOrderTest() {
        Order order = new Order(1, "client_1", new Date(), "address");

        orderService.save(order);

        verify(orderDao, times(1)).save(order);
    }

    @Test
    public void deleteOrderByIdTest() {
        orderService.deleteById(1);

        verify(orderDao, times(1)).deleteById(1);
    }
}
