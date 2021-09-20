package ru.tarelkin.spring_backend_interview.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.tarelkin.spring_backend_interview.model.*;
import ru.tarelkin.spring_backend_interview.service.GoodsService;
import ru.tarelkin.spring_backend_interview.service.OrderLineService;
import ru.tarelkin.spring_backend_interview.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderLineService orderLineService;
    @MockBean
    private GoodsService goodsService;

    @Test
    public void getAllOrderTest() throws Exception {
        List<Order> orderList = Arrays.asList(new Order(1, "client_1", new Date(), "address_1"),
                new Order(2, "client_2", new Date(), "address_2"));

        when(orderService.findAll()).thenReturn(orderList);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].client").value("client_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("address_1"));
        verify(orderService, times(1)).findAll();
    }

    @Test
    public void getOrderInfoByIdTest() throws Exception {
        int countGoodsNotInOrder = 4;
        Integer id = 1;
        Order clientInfo = new Order(id, "client_1", new Date(), "address");
        List<GoodsInOrder> goodsInOrder = Arrays.asList(new GoodsInOrder(1, "goods_1", 1, 10),
                new GoodsInOrder(2, "goods_2", 2, 20));

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setClientInfo(clientInfo);
        orderInfo.setGoodsInOrder(goodsInOrder);
        orderInfo.setCountGoodsNotInOrder(countGoodsNotInOrder);

        List<Goods> allGoods = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            allGoods.add(new Goods());
        }

        when(orderService.findById(id)).thenReturn(clientInfo);
        when(orderLineService.getGoodsListByOrderId(id)).thenReturn(goodsInOrder);
        when(goodsService.findAll()).thenReturn(allGoods);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientInfo.client").value("client_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientInfo.address").value("address"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goodsInOrder.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countGoodsNotInOrder").value(4));
        verify(orderService, times(1)).findById(id);
        verify(orderLineService, times(1)).getGoodsListByOrderId(id);
        verify(goodsService, times(1)).findAll();
    }

    @Test
    public void createOrderTest() throws Exception {
        Integer orderId = 1;
        Integer goodsId = 1;
        int count = 2;
        Date date = new Date();
        Order order = new Order(null, "client", date, "address");
        Order savedOrder = new Order(orderId, "client", date, "address");
        Goods goods = new Goods(goodsId, "goods_1", 10);
        OrderLine orderLine = new OrderLine();
        orderLine.setCount(count);

        OrderLine savedOrderLine = new OrderLine();
        savedOrderLine.setId(1);
        savedOrderLine.setOrder(order);
        savedOrderLine.setGoods(goods);
        savedOrderLine.setCount(count);

        List<GoodsInOrder> goodsInOrder = Arrays.asList(new GoodsInOrder(1, "goods_1", count, 10));

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setClientInfo(order);
        orderInfo.setGoodsInOrder(goodsInOrder);

        when(orderService.save(order)).thenReturn(savedOrder);
        when(goodsService.findById(goodsId)).thenReturn(goods);
        when(orderLineService.save(orderLine)).thenReturn(savedOrderLine);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderInfo)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientInfo.id").value(orderId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientInfo.client").value("client"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientInfo.address").value("address"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goodsInOrder.size()").value(1));
        verify(orderService, times(1)).save(order);
        verify(goodsService, times(1)).findById(goodsId);
        verify(orderLineService, times(1)).save(orderLine);
    }

    @Test
    public void editClientInfoTest() throws Exception {
        Integer id = 1;
        Order order = new Order(id, "client", new Date(), "address");

        when(orderService.findById(id)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}/edit-client-info", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.client").value("client"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("address"));
        verify(orderService, times(1)).findById(id);
    }

    @Test
    public void updateClientInfoTest() throws Exception {
        Integer id = 1;
        Order order = new Order(id, "client", new Date(), "address");
        Order updatedOrder = new Order(id, "new_client", new Date(), "new_address");

        when(orderService.findById(id)).thenReturn(order);
        when(orderService.save(order)).thenReturn(updatedOrder);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/{id}/update-client-info", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedOrder)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client").value("new_client"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("new_address"));
        verify(orderService, times(1)).findById(id);
        verify(orderService, times(1)).save(updatedOrder);
    }

    @Test
    public void editGoodsInOrderTest() throws Exception {
        Integer id = 1;
        List<GoodsInOrder> goodsInOrderList = Arrays.asList(new GoodsInOrder(1, "goods", 2, 10),
                new GoodsInOrder(2, "goods_2", 3, 20));

        when(orderLineService.getGoodsListByOrderId(id)).thenReturn(goodsInOrderList);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}/edit-goods", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("goods"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("goods_2"));

        verify(orderLineService, times(1)).getGoodsListByOrderId(id);
    }

    @Test
    public void updateGoodsInOrderTest() throws Exception {
        Integer id = 1;
        Order order = new Order(id, "client", new Date(), "address");
        Goods goods = new Goods(id, "goods", 10);
        int count = 5;
        OrderLine orderLine = new OrderLine();
        orderLine.setId(id);
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(count);

        List<GoodsInOrder> goodsInOrderList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GoodsInOrder goodsInOrder = new GoodsInOrder(1, "goods_2", 3, 20);
            if (i%2!=0) goodsInOrder.setDeleted(true);
            goodsInOrderList.add(goodsInOrder);
        }

        when(orderService.findById(id)).thenReturn(order);
        when(goodsService.findById(id)).thenReturn(goods);
        when(orderLineService.findOrderLineByOrderAndGoods(order, goods)).thenReturn(orderLine);
        when(orderLineService.save(orderLine)).thenReturn(orderLine);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/{id}/update-goods", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goodsInOrderList)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists());
        verify(orderService, times(1)).findById(id);
        verify(goodsService, times(3)).findById(id);
        verify(orderLineService, times(3)).findOrderLineByOrderAndGoods(order, goods);
        verify(orderLineService, times(1)).delete(orderLine);
        verify(orderLineService, times(2)).save(orderLine);
    }

    @Test
    public void getGoodsNotInOrderTest() throws Exception {
        Integer id = 1;
        List<GoodsInOrder> goodsInOrderList = Arrays.asList(new GoodsInOrder(1, "goods_1", 3, 10),
                new GoodsInOrder(3, "goods_3", 3, 30));

        List<Goods> goodsList = Arrays.asList(new Goods(1, "goods_1", 10),
                new Goods(2, "goods_2", 10),
                new Goods(3, "goods_3", 10),
                new Goods(4, "goods_4", 10));

        when(orderLineService.getGoodsListByOrderId(id)).thenReturn(goodsInOrderList);
        when(goodsService.findAll()).thenReturn(goodsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}/add-goods", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(4));
        verify(orderLineService, times(1)).getGoodsListByOrderId(id);
        verify(goodsService, times(1)).findAll();
    }

    @Test
    public void addGoodsInOrderTest() throws Exception {
        Integer id = 1;
        Order order = new Order(id, "client", new Date(), "address");

        Goods goods = new Goods(id, "goods", 10);

        OrderLine orderLine = new OrderLine();
        orderLine.setCount(5);

        List<GoodsInOrder> addedGoods = Arrays.asList(new GoodsInOrder(1, "goods", 5, 10),
                new GoodsInOrder(2, "goods", 0, 10),
                new GoodsInOrder(3, "goods", 5, 10));

        when(orderService.findById(id)).thenReturn(order);
        when(goodsService.findByName("goods")).thenReturn(goods);
        when(orderLineService.save(orderLine)).thenReturn(orderLine);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/{id}/add-goods", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addedGoods)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(3));
        verify(orderService, times(1)).findById(id);
        verify(goodsService, times(2)).findByName("goods");
        verify(orderLineService, times(2)).save(orderLine);
    }

    @Test
    public void deleteOrderTest() throws Exception {
        Integer id = 1;
        Order order = new Order(id, "client", new Date(), "address");

        when(orderService.findById(id)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists());

        verify(orderService, times(1)).findById(id);
        verify(orderService ,times(1)).deleteById(id);
    }
}
