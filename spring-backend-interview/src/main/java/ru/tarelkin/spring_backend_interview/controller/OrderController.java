package ru.tarelkin.spring_backend_interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tarelkin.spring_backend_interview.dao.OrderDao;
import ru.tarelkin.spring_backend_interview.dao.OrderLineDao;
import ru.tarelkin.spring_backend_interview.model.*;
import ru.tarelkin.spring_backend_interview.service.GoodsService;
import ru.tarelkin.spring_backend_interview.service.OrderLineService;
import ru.tarelkin.spring_backend_interview.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private GoodsService goodsService;
    private OrderService orderService;
    private OrderLineService orderLineService;

    @Autowired
    public OrderController(GoodsService goodsService, OrderService orderService, OrderLineService orderLineService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
    }

    @GetMapping
    public List<Order> orders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderInfo getOrderById(@PathVariable Integer id) {
        Order clientInfo = orderService.findById(id);

        List<GoodsList> goodsInList = orderLineService.getGoodsListByOrderId(id);

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
        Order order = orderService.save(orderNew);

        for (GoodsList goodsList : orderInfo.getGoodsInList()) {
            if (goodsList.getCount() != 0) {
                OrderLine orderLineNew = new OrderLine();
                orderLineNew.setOrder(order);
                orderLineNew.setGoods(goodsService.findById(goodsList.getId()));
                orderLineNew.setCount(goodsList.getCount());
                orderLineService.save(orderLineNew);
            }
        }
        return orderInfo;
    }

    @GetMapping("/{id}/edit")
    public OrderInfo editOrder(@PathVariable Integer id) {
        Order clientInfo = orderService.findById(id);

        List<GoodsList> goodsInList = orderLineService.getGoodsListByOrderId(id);

        List<Integer> goodsIds = new ArrayList<>();
        goodsInList.forEach(g->goodsIds.add(g.getId()));
        List<Goods> goodsNotInList = goodsService.findAll().stream().filter(g->!goodsIds.contains(g.getId())).collect(Collectors.toList());

        OrderInfo orderinfo = new OrderInfo();
        orderinfo.setClientInfo(clientInfo);
        orderinfo.setGoodsInList(goodsInList);
        orderinfo.setGoodsNotInList(goodsNotInList);

        return orderinfo;
    }

    @PatchMapping("/{id}")
    public OrderInfo updateOrder(@PathVariable Integer id, @RequestBody OrderInfo orderInfo) {
        Order order = orderService.findById(id);
        order.setClient(orderInfo.getClientInfo().getClient());
        order.setAddress(orderInfo.getClientInfo().getAddress());

        for (GoodsList goodsList : orderInfo.getGoodsInList()) {
            Goods goods = goodsService.findById(goodsList.getId());
            OrderLine orderLine = orderLineService.findOrderLineByOrderAndGoods(order, goods);
            if (goodsList.isDeleted()) {
                orderLineService.delete(orderLine);
            } else {
                orderLine.setCount(goodsList.getCount());
            }
        }

        if (orderInfo.getAddedGoods().getName() != null && !orderInfo.getAddedGoods().getName().isEmpty()
                && orderInfo.getAddedGoods().getCount() != 0) {
            OrderLine orderLineNew = new OrderLine();
            orderLineNew.setOrder(order);
            Goods g = goodsService.findByName(orderInfo.getAddedGoods().getName());
            System.out.println(g);
            orderLineNew.setGoods(g);
            orderLineNew.setCount(orderInfo.getAddedGoods().getCount());
            orderLineService.save(orderLineNew);
        }

        return orderInfo;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Integer id) {
        Order order = orderService.findById(id);
        orderService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
