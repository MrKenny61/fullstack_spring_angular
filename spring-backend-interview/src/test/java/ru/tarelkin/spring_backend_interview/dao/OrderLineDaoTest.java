package ru.tarelkin.spring_backend_interview.dao;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tarelkin.spring_backend_interview.model.Goods;
import ru.tarelkin.spring_backend_interview.model.GoodsInOrder;
import ru.tarelkin.spring_backend_interview.model.Order;
import ru.tarelkin.spring_backend_interview.model.OrderLine;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
public class OrderLineDaoTest {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderLineDao orderLineDao;

    private static Integer idOrder = 1;
    private static Integer idGoods = 1;

    @Before
    public void setUpData() {
        System.out.println("OrderLineDaoTest::setUpData()");

        Order order = new Order();
        order.setClient("client_" + idOrder);
        order.setAddress("address");
        Order o = orderDao.save(order);
        idOrder = o.getId();

        Goods goods = new Goods();
        goods.setName("goods_" + idGoods);
        goods.setPrice(10);
        Goods g = goodsDao.save(goods);
        idGoods = g.getId();

        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(2);
        OrderLine ol = orderLineDao.save(orderLine);
    }

    @Test
    public void saveOrderLineTest() {
        System.out.println("OrderLineDaoTest::saveOrderLineTest()");

        Order order = new Order();
        order.setClient("client_" + (idOrder+1));
        order.setAddress("address");
        Order o = orderDao.save(order);

        Goods goods = new Goods();
        goods.setName("goods_" + (idGoods+1));
        goods.setPrice(10);
        Goods g = goodsDao.save(goods);

        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(2);
        OrderLine ol = orderLineDao.save(orderLine);

        Assertions.assertThat(ol).isNotNull();
    }

    @Test
    public void deleteOrderLineTest() {
        System.out.println("OrderLineDaoTest::deleteOrderLineTest()");

        System.out.println(idOrder + " " + idGoods);
        Order order = orderDao.findById(idOrder).get();
        Goods goods = goodsDao.findById(idGoods).get();

        Optional<OrderLine> orderLine = orderLineDao.findOrderLineByOrderAndGoods(order, goods);
        Boolean orderLineExist = orderLine.isPresent();
        orderLineDao.delete(orderLine.get());
        Boolean orderLineNotExist = orderLineDao.findOrderLineByOrderAndGoods(order, goods).isPresent();

        Assertions.assertThat(orderLineExist).isTrue();
        Assertions.assertThat(orderLineNotExist).isFalse();
    }

    @Test
    public void getGoodsInOrderByOrderIdTest() {
        System.out.println("OrderLineDaoTest::getGoodsListByOrderIdTest()");

        List<GoodsInOrder> goodsInOrder = orderLineDao.getGoodsInOrderByOrderId(idOrder);

        Assertions.assertThat(goodsInOrder).isNotNull();
    }

    @Test
    public void findOrderLineByOrderAndGoodsTest() {
        System.out.println("OrderLineDaoTest::findOrderLineByOrderAndGoodsTest()");

        Order order = orderDao.findById(idOrder).get();
        Goods goods = goodsDao.findById(idGoods).get();

        Boolean orderLine = orderLineDao.findOrderLineByOrderAndGoods(order, goods).isPresent();

        Assertions.assertThat(orderLine).isTrue();
    }
}
