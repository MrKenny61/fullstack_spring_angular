package ru.tarelkin.spring_backend_interview.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tarelkin.spring_backend_interview.model.Order;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
public class OrderDaoTest {
    @Autowired
    private OrderDao orderDao;

    private static Integer id = 1;

    @Before
    public void setUpData() {
        System.out.println("OrderDaoTest::setUpData()");

        Order order = new Order();
        order.setClient("client_" + id);
        order.setAddress("address");

        Order o = orderDao.save(order);
        id = o.getId();
    }

    @Test
    public void saveOrderTest() {
        System.out.println("OrderDaoTest::saveOrderTest()");

        Order order = new Order();
        order.setClient("client");
        order.setAddress("address");

        Order savedOrder = orderDao.save(order);

        Assertions.assertThat(savedOrder).isNotNull();
    }

    @Test
    public void findAllOrdersTest(){
        System.out.println("OrderDaoTest::findAllOrdersTest()");

        List<Order> orderList = orderDao.findAll();

        Assertions.assertThat(orderList.size()).isGreaterThan(0);
    }

    @Test
    public void findOrderByIdExistTest() {
        System.out.println("OrderDaoTest::findOrderByIdExistTest()");

        Boolean order = orderDao.findById(id).isPresent();

        Assertions.assertThat(order).isTrue();
    }

    @Test
    public void findOrderByIdNotExistTest() {
        System.out.println("OrderDaoTest::findOrderByIdNotExistTest()");

        Boolean order = orderDao.findById(-1).isPresent();

        Assertions.assertThat(order).isFalse();
    }

    @Test
    public void updateOrderTest() {
        System.out.println("OrderDaoTest::updateOrderTest()");

        Order order = orderDao.findById(id).get();
        order.setClient("newClient");
        order.setAddress("newAddress");

        Order updatedOrder = orderDao.save(order);

        Assertions.assertThat(updatedOrder.getClient()).isEqualTo(order.getClient());
        Assertions.assertThat(updatedOrder.getAddress()).isEqualTo(order.getAddress());
    }

    @Test
    public void deleteOrderByIdTest() {
        System.out.println("OrderDaoTest::deleteOrderByIdTest()");

        Boolean orderExist = orderDao.findById(id).isPresent();
        orderDao.deleteById(id);
        Boolean orderNotExist = orderDao.findById(id).isPresent();

        Assertions.assertThat(orderExist).isTrue();
        Assertions.assertThat(orderNotExist).isFalse();
    }
}
