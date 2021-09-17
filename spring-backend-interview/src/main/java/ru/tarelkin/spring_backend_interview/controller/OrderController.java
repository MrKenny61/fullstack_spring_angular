package ru.tarelkin.spring_backend_interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tarelkin.spring_backend_interview.dao.GoodsDao;
import ru.tarelkin.spring_backend_interview.dao.OrderDao;
import ru.tarelkin.spring_backend_interview.dao.OrderLineDao;
import ru.tarelkin.spring_backend_interview.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private OrderDao orderDao;
    private OrderLineDao orderLineDao;
    private GoodsDao goodsDao;

    @Autowired
    public OrderController(OrderDao orderDao, OrderLineDao orderLineDao, GoodsDao goodsDao) {
        this.orderDao = orderDao;
        this.orderLineDao = orderLineDao;
        this.goodsDao = goodsDao;
    }

    @GetMapping
    public List<Order> orders() {
        return orderDao.findAll();
    }

    @GetMapping("/{id}")
    public OrderInfo getOrderById(@PathVariable Integer id) {
        Order clientInfo = orderDao.findById(id).orElseThrow();

        List<GoodsList> goodsInList = orderLineDao.getGoodsListByOrderId(id);

        OrderInfo orderinfo = new OrderInfo();
        orderinfo.setClientInfo(clientInfo);
        orderinfo.setGoodsInList(goodsInList);

        return orderinfo;
    }

    @PostMapping
    public OrderInfo createOrder(@RequestBody OrderInfo orderInfo) {
        Order orderNew = new Order();
        orderNew.setClient(orderInfo.getClientInfo().getClient());
        orderNew.setAddress(orderInfo.getClientInfo().getAddress());
        Order order = orderDao.save(orderNew);

        for (GoodsList goodsList : orderInfo.getGoodsInList()) {
            if (goodsList.getCount() != 0) {
                OrderLine orderLineNew = new OrderLine();
                orderLineNew.setOrder(order);
                orderLineNew.setGoods(goodsDao.findById(goodsList.getId()).orElseThrow());
                orderLineNew.setCount(goodsList.getCount());
                orderLineDao.save(orderLineNew);
            }
        }
        return orderInfo;
    }

    @GetMapping("/{id}/edit")
    public OrderInfo editOrder(@PathVariable Integer id) {
        Order clientInfo = orderDao.findById(id).orElseThrow();

        List<GoodsList> goodsInList = orderLineDao.getGoodsListByOrderId(id);

        List<Integer> goodsIds = new ArrayList<>();
        goodsInList.forEach(g->goodsIds.add(g.getId()));
        List<Goods> goodsNotInList = goodsDao.findAll().stream().filter(g->!goodsIds.contains(g.getId())).collect(Collectors.toList());

        OrderInfo orderinfo = new OrderInfo();
        orderinfo.setClientInfo(clientInfo);
        orderinfo.setGoodsInList(goodsInList);
        orderinfo.setGoodsNotInList(goodsNotInList);

        return orderinfo;
    }

    @PatchMapping("/{id}")
    public OrderInfo updateOrder(@PathVariable Integer id, @RequestBody OrderInfo orderInfo) {
        Order order = orderDao.findById(id).orElseThrow();
        order.setClient(orderInfo.getClientInfo().getClient());
        order.setAddress(orderInfo.getClientInfo().getAddress());

        for (GoodsList goodsList : orderInfo.getGoodsInList()) {
            Goods goods = goodsDao.findById(goodsList.getId()).orElseThrow();
            OrderLine orderLine = orderLineDao.findOrderLineByOrderAndGoods(order, goods);
            if (goodsList.isDeleted()) {
                orderLineDao.delete(orderLine);
            } else {
                orderLine.setCount(goodsList.getCount());
            }
        }

        if (orderInfo.getAddedGoods().getName() != null && !orderInfo.getAddedGoods().getName().isEmpty()
                && orderInfo.getAddedGoods().getCount() != 0) {
            OrderLine orderLineNew = new OrderLine();
            orderLineNew.setOrder(order);
            orderLineNew.setGoods(goodsDao.findByName(orderInfo.getAddedGoods().getName()).orElseThrow());
            orderLineNew.setCount(orderInfo.getAddedGoods().getCount());
            orderLineDao.save(orderLineNew);
        }

        return orderInfo;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Integer id) {
        Order order = orderDao.findById(id).orElseThrow();
        orderDao.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
