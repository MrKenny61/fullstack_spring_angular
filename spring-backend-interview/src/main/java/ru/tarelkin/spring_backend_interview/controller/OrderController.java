package ru.tarelkin.spring_backend_interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

        List<GoodsInOrder> goodsInOrder = orderLineService.getGoodsListByOrderId(id);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setClientInfo(clientInfo);
        orderInfo.setGoodsInOrder(goodsInOrder);

        List<Integer> goodsIds = new ArrayList<>();
        orderInfo.getGoodsInOrder().forEach(g->goodsIds.add(g.getId()));
        List<Goods> g = goodsService.findAll();
        int countGoodsNotInOrder = g.size() - goodsInOrder.size();
        orderInfo.setCountGoodsNotInOrder(countGoodsNotInOrder);

        return orderInfo;
    }

    @PostMapping
    public OrderInfo createOrder(@RequestBody OrderInfo orderInfo) {
        Order orderNew = new Order();
        orderNew.setClient(orderInfo.getClientInfo().getClient());
        orderNew.setAddress(orderInfo.getClientInfo().getAddress());
        Order order = orderService.save(orderNew);
        orderInfo.setClientInfo(order);

        for (GoodsInOrder goodsInOrder : orderInfo.getGoodsInOrder()) {
            if (goodsInOrder.getCount() != 0) {
                OrderLine orderLineNew = new OrderLine();
                orderLineNew.setOrder(order);
                orderLineNew.setGoods(goodsService.findById(goodsInOrder.getId()));
                orderLineNew.setCount(goodsInOrder.getCount());
                orderLineService.save(orderLineNew);
            }
        }
        return orderInfo;
    }

    @GetMapping("/{id}/edit-client-info")
    public Order editClientInfo(@PathVariable Integer id) {
        return orderService.findById(id);
    }

    @PatchMapping("/{id}/update-client-info")
    public Order updateClientInfo(@PathVariable Integer id, @RequestBody Order clientInfo) {
        Order order = orderService.findById(id);
        order.setClient(clientInfo.getClient());
        order.setAddress(clientInfo.getAddress());
        orderService.save(order);

        return order;
    }

    @GetMapping("/{id}/edit-goods")
    public List<GoodsInOrder> editGoodsInOrder(@PathVariable Integer id) {
        return orderLineService.getGoodsListByOrderId(id);
    }

    @PatchMapping("/{id}/update-goods")
    public List<GoodsInOrder> updateGoodsInOrder(@PathVariable Integer id, @RequestBody List<GoodsInOrder> goodsInOrderList) {
        Order order = orderService.findById(id);

        for (GoodsInOrder goodsInOrder : goodsInOrderList) {
            Goods goods = goodsService.findById(goodsInOrder.getId());
            OrderLine orderLine = orderLineService.findOrderLineByOrderAndGoods(order, goods);
            if (goodsInOrder.isDeleted()) {
                orderLineService.delete(orderLine);
            } else {
                orderLine.setCount(goodsInOrder.getCount());
                orderLineService.save(orderLine);
            }
        }

        return goodsInOrderList;
    }

    @GetMapping("/{id}/add-goods")
    public List<Goods> getGoodsNotInOrder(@PathVariable Integer id) {
        List<Integer> goodsIds = new ArrayList<>();
        List<GoodsInOrder> goodsInOrder = orderLineService.getGoodsListByOrderId(id);
        goodsInOrder.forEach(g->goodsIds.add(g.getId()));

        return goodsService.findAll()
                .stream().filter(g->!goodsIds.contains(g.getId())).collect(Collectors.toList());
    }

    @PatchMapping("/{id}/add-goods")
    public List<GoodsInOrder> addGoodsInOrder(@PathVariable Integer id, @RequestBody List<GoodsInOrder> goods) {
        Order order = orderService.findById(id);
        List<GoodsInOrder> addedGoods = new ArrayList<>();

        for (GoodsInOrder goodsInOrder : goods) {
            if (goodsInOrder.getCount() != 0) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrder(order);
                orderLine.setGoods(goodsService.findByName(goodsInOrder.getName()));
                orderLine.setCount(goodsInOrder.getCount());
                orderLineService.save(orderLine);
                addedGoods.add(goodsInOrder);
            }
        }

        return addedGoods;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Integer id) {
        orderService.findById(id);
        orderService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
